package woojjam.utrip.course.service;

import woojjam.utrip.common.exception.NoSuchElementException;
import woojjam.utrip.common.exception.RuntimeException;
import woojjam.utrip.common.exception.UserException;
import woojjam.utrip.common.reponse.StatusCode;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.course.domain.CourseDetail;
import woojjam.utrip.course.domain.UserCourse;
import woojjam.utrip.course.domain.VideoCourse;
import woojjam.utrip.course.dto.*;
import woojjam.utrip.place.dto.PlaceDto;
import woojjam.utrip.course.repository.CourseDetailRepository;
import woojjam.utrip.course.repository.UserCourseRepository;
import woojjam.utrip.course.repository.VideoCourseRepository;
import woojjam.utrip.place.domain.Place;
import woojjam.utrip.place.repository.PlaceRepository;
import woojjam.utrip.user.domain.User;
import woojjam.utrip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CourseService {

    private final UserCourseRepository userCourseRepository;
    private final VideoCourseRepository videoCourseRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final CourseDetailRepository courseDetailRepository;

    public ResponseEntity<?> postUserCourse(CourseListDto courseListDto, String email) {
        Optional<User> findUser = userRepository.findByEmail(email);
        User user = findUser.orElseThrow(() -> new UserException(StatusCode.USER_NOT_FOUND));

        String courseName = courseListDto.getName();
        UserCourse userCourse = saveUserCourse(user, courseName);

        List<PlanDto> courses = courseListDto.getPlan();
        courses.forEach(course -> {
            int day = course.getDay();
            List<PlaceDto> places = course.getPlace();
            String placesString = generatePlacesString(places);
            CourseDetail courseDetail = CourseDetailDto.toEntity(placesString, userCourse, day);
            courseDetailRepository.save(courseDetail);
        });

        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }

    private String generatePlacesString(List<PlaceDto> courses) {
        return courses.stream().map(this::getPlaceId).collect(Collectors.joining(","));
    }

    private String getPlaceId(PlaceDto course) {
        double posX = course.getPosX();
        double posY = course.getPosY();
        Optional<Place> findPlace = placeRepository.findByPxAndPy(posX, posY);
        if (findPlace.isEmpty()) {
            Place place = PlaceDto.toEntity(course, posX, posY);
            placeRepository.save(place);
            return String.valueOf(place.getId());
        }
        return String.valueOf(findPlace.get().getId());
    }

    private UserCourse saveUserCourse(User user, String courseName) {
        try {
            UserCourse userCourse = UserCourseDto.toEntity(user, courseName);
            return userCourseRepository.save(userCourse);
        } catch (Exception e) {
            throw new RuntimeException(StatusCode.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<?> getVideoCourse(Long videoId) {
        Optional<VideoCourse> findVideoCourse = videoCourseRepository.findByVideoId(videoId);
        VideoCourse videoCourse = findVideoCourse.orElseThrow(() -> new NoSuchElementException(StatusCode.VIDEO_NOT_FOUND));

        List<String> findPlaceIds = List.of(videoCourse.getPlaces().split(","));

        AtomicInteger index = new AtomicInteger();
        List<PlaceDto> placeDto = findPlaceIds.stream().map(placeId -> {
            index.getAndIncrement();
            Optional<Place> findPlace = placeRepository.findById(Long.parseLong(placeId));
            Place place = findPlace.orElseThrow(() -> new NoSuchElementException(StatusCode.PLACE_NOT_FOUND));
            return PlaceDto.of(index.get(), place);
        }).toList();

        CourseResponse response = CourseResponse.from(placeDto);
        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), response));
    }
}
