package woojjam.utrip.course.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.exception.NoSuchElementException;
import woojjam.utrip.common.exception.RuntimeException;
import woojjam.utrip.common.exception.UserException;
import woojjam.utrip.common.reponse.StatusCode;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.course.domain.CourseDetail;
import woojjam.utrip.course.domain.UserCourse;
import woojjam.utrip.course.domain.UserCourseDay;
import woojjam.utrip.course.dto.CourseDetailDto;
import woojjam.utrip.course.dto.CourseListDto;
import woojjam.utrip.course.dto.CourseResponse;
import woojjam.utrip.course.dto.PlanDto;
import woojjam.utrip.course.dto.UserCourseDayDto;
import woojjam.utrip.course.dto.UserCourseDto;
import woojjam.utrip.course.repository.CourseDetailRepository;
import woojjam.utrip.course.repository.UserCourseDayRepository;
import woojjam.utrip.course.repository.UserCourseRepository;
import woojjam.utrip.place.domain.Place;
import woojjam.utrip.place.dto.PlaceDto;
import woojjam.utrip.place.repository.PlaceRepository;
import woojjam.utrip.user.domain.User;
import woojjam.utrip.user.repository.UserRepository;
import woojjam.utrip.video.domain.Video;
import woojjam.utrip.video.repository.VideoRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CourseService {

	private final UserCourseRepository userCourseRepository;
	private final VideoRepository videoRepository;
	private final UserRepository userRepository;
	private final PlaceRepository placeRepository;
	private final UserCourseDayRepository userCourseDayRepository;
	private final CourseDetailRepository courseDetailRepository;

	public void postUserCourse(CourseListDto courseListDto, String email) {
		Optional<User> findUser = userRepository.findByEmail(email);
		User user = findUser.orElseThrow(() -> new UserException(StatusCode.USER_NOT_FOUND));

		String courseName = courseListDto.getName();
		UserCourse userCourse = saveUserCourse(user, courseName);

		List<PlanDto> courses = courseListDto.getPlan();
		List<Double> posXs = new ArrayList<>();
		List<Double> posYs = new ArrayList<>();

		courses.forEach(course -> course.getPlace().forEach(p -> {
			posXs.add(p.getPosX());
			posYs.add(p.getPosY());
		}));

		List<Place> findPlaces = placeRepository.findByPxInAndPyIn(posXs, posYs);
		Map<String, Place> placeMap = findPlaces.stream()
			.collect(Collectors.toMap(p -> p.getPx() + "," + p.getPy(), p -> p));

		courses.forEach(course -> {

			int day = course.getDay();
			UserCourseDay userCourseDay = UserCourseDayDto.toEntity(day, userCourse);
			userCourseDayRepository.save(userCourseDay);

			course.getPlace().forEach(p -> {
				String key = p.getPosX() + "," + p.getPosY();
				Place place = placeMap.get(key);

				if (place == null) {
					place = placeRepository.save(PlaceDto.toEntity(p));
					placeMap.put(key, place);
				}

				CourseDetail courseDetail = CourseDetailDto.toEntity(place, userCourseDay, p.getOrder());
				courseDetailRepository.save(courseDetail);
			});
		});
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

		Video videoCourse = videoRepository.findById(videoId)
			.orElseThrow(() -> new NoSuchElementException(StatusCode.VIDEO_NOT_FOUND));
		Place place = videoCourse.getPlace();

		PlaceDto placeDto = PlaceDto.from(place);

		CourseResponse response = CourseResponse.from(placeDto);

		return ResponseEntity.ok(
			SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), response));
	}
}
