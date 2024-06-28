package woojjam.utrip.user.service;

import woojjam.utrip.common.exception.NoSuchElementException;
import woojjam.utrip.common.exception.RuntimeException;
import woojjam.utrip.common.exception.UserException;
import woojjam.utrip.common.reponse.StatusCode;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.course.domain.CourseDetail;
import woojjam.utrip.course.domain.UserCourse;
import woojjam.utrip.course.dto.CourseDetailDto;
import woojjam.utrip.course.dto.CourseDto;
import woojjam.utrip.course.dto.CourseListDto;
import woojjam.utrip.course.dto.PlanDto;
import woojjam.utrip.course.repository.CourseDetailRepository;
import woojjam.utrip.like.repository.VideoLikeRepository;
import woojjam.utrip.place.dto.PlaceDto;
import woojjam.utrip.course.repository.UserCourseRepository;
import woojjam.utrip.place.domain.Place;
import woojjam.utrip.place.repository.PlaceRepository;
import woojjam.utrip.user.domain.User;
import woojjam.utrip.user.dto.UserCourseResponse;
import woojjam.utrip.user.repository.UserRepository;
import woojjam.utrip.video.domain.Video;
import woojjam.utrip.video.dto.VideoListDto;
import woojjam.utrip.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final VideoRepository videoRepository;
    private final UserCourseRepository userCourseRepository;
    private final CourseDetailRepository courseDetailRepository;
    private final VideoLikeRepository videoLikeRepository;

//    @Transactional(readOnly = true)
//    public ResponseEntity<?> findUserCourse(Long userId) {
//        userRepository.findById(userId).orElseThrow(
//                () -> new UserException(StatusCode.USER_NOT_FOUND)
//        );
//
//        List<UserCourse> userCourses = userCourseRepository.findByUserId(userId);
//        List<CourseDto> courseDto = userCourses.stream()
//                .map(this::convertToCourseDto)
//                .toList();
//
//        UserCourseResponse response = UserCourseResponse.from(courseDto);
//        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), response));
//    }

//    private CourseDto convertToCourseDto(UserCourse userCourse) {
//        List<PlanDto> planDto = userCourse.getCourseDetails().stream()
//                .map(this::convertToPlanDto)
//                .sorted(Comparator.comparingInt(PlanDto::getDay))
//                .toList();
//
//        return CourseDto.of(userCourse.getId(), userCourse.getName(), userCourse.getCreatedAt(), userCourse.getUpdatedAt(), planDto);
//    }

//    private PlanDto convertToPlanDto(CourseDetail courseDetail) {
//        List<Long> placeIds = Arrays.stream(courseDetail.getPlaces().split(","))
//                .map(Long::parseLong)
//                .toList();
//
//        AtomicInteger index = new AtomicInteger();
//        List<PlaceDto> placeDto = placeIds.stream().map(placeId -> {
//            Place place = placeRepository.findById(placeId).orElseThrow(() -> new NoSuchElementException(StatusCode.PLACE_NOT_FOUND));
//            return PlaceDto.of(index.incrementAndGet(), place);
//        }).toList();
//
//        return PlanDto.of(courseDetail.getDayNum(), placeDto);
//    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getUserLikeVideo(Long userId) {
        List<Long> videoLikes = videoLikeRepository.findByUserId(userId).stream().map(videoLike -> videoLike.getVideo().getId()).toList();
        List<Video> findVideos = videoRepository.findByIdIn(videoLikes);
        List<VideoListDto> videoList = findVideos.stream().map(VideoListDto::from).toList();
        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), videoList));
    }

    public ResponseEntity<?> deleteUserLikeVideo(Long userId, Long videoId) {
        videoLikeRepository.deleteByUserIdAndVideoId(userId, videoId);
        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }

    public ResponseEntity<?> deleteUserCourse(Long userCourseId) {
        try {
            courseDetailRepository.deleteAllByUserCourseId(userCourseId);
            userCourseRepository.deleteById(userCourseId);
        } catch (Exception e) {
            throw new RuntimeException(StatusCode.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }

    public ResponseEntity<?> updateUserCourse(Long userId, Long userCourseId, CourseListDto courseListDto) {
        log.info("user id = {} , userCourse Id = {}", userId, userCourseId);
        // UserCourse 조회
        UserCourse userCourse = userCourseRepository.findById(userCourseId).orElseThrow(() -> new NoSuchElementException(StatusCode.USER_COURSE_NOT_FOUND));

        log.info("User Course = {}", userCourse);
        // User 조회
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException(StatusCode.USER_NOT_FOUND));

        courseDetailRepository.deleteAllByUserCourseId(userCourseId);
        courseListDto.getPlan().forEach(courseList -> {
            List<PlaceDto> places = courseList.getPlace();
            String placeIds = places.stream().map(p -> {
                double posX = p.getPosX();
                double posY = p.getPosY();
                Optional<Place> findPlaces = placeRepository.findByPxAndPy(posX, posY);
                if (findPlaces.isEmpty()) {
                    Place place = PlaceDto.toEntity(p);
                    return placeRepository.save(place).getId().toString();
                }
                return findPlaces.get().getId().toString();
            }).collect(Collectors.joining(","));
//            CourseDetail courseDetail = CourseDetailDto.toEntity(placeIds, userCourse, courseList.getDay());
//            courseDetailRepository.save(courseDetail);
        });

        log.info("After Course Detail Update Query");
        userCourse.updateUserCourse(user, courseListDto);

        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }
}


