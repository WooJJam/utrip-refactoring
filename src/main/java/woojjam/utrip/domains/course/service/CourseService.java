package woojjam.utrip.domains.course.service;

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
import woojjam.utrip.common.exception.RuntimeException;
import woojjam.utrip.common.exception.StatusCode;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.domains.course.domain.CourseDetail;
import woojjam.utrip.domains.course.domain.UserCourse;
import woojjam.utrip.domains.course.domain.UserCourseDay;
import woojjam.utrip.domains.course.dto.CourseDetailDto;
import woojjam.utrip.domains.course.dto.CourseListDto;
import woojjam.utrip.domains.course.dto.CourseResponse;
import woojjam.utrip.domains.course.dto.PlanDto;
import woojjam.utrip.domains.course.dto.UserCourseDayDto;
import woojjam.utrip.domains.course.dto.UserCourseDto;
import woojjam.utrip.domains.course.repository.CourseDetailRepository;
import woojjam.utrip.domains.course.repository.UserCourseDayRepository;
import woojjam.utrip.domains.course.repository.UserCourseRepository;
import woojjam.utrip.domains.place.domain.Place;
import woojjam.utrip.domains.place.dto.PlaceDto;
import woojjam.utrip.domains.place.repository.PlaceRepository;
import woojjam.utrip.domains.user.domain.User;
import woojjam.utrip.domains.user.exception.UserErrorCode;
import woojjam.utrip.domains.user.exception.UserException;
import woojjam.utrip.domains.user.repository.UserRepository;
import woojjam.utrip.domains.video.domain.Video;
import woojjam.utrip.domains.video.repository.VideoRepository;

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
		User user = findUser.orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

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

		Video videoCourse = videoRepository.findById(videoId).get();
		// .orElseThrow(() -> new NoSuchElementException(StatusCode.VIDEO_NOT_FOUND));
		Place place = videoCourse.getPlace();

		PlaceDto placeDto = PlaceDto.from(place);

		CourseResponse response = CourseResponse.from(placeDto);

		return ResponseEntity.ok(
			SuccessResponse.of(StatusCode.SUCCESS, response));
	}
}
