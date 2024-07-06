package woojjam.utrip.domains.user.service;

import java.time.LocalDateTime;
import java.util.Comparator;
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
import woojjam.utrip.domains.course.domain.UserCourse;
import woojjam.utrip.domains.course.dto.CourseDto;
import woojjam.utrip.domains.course.dto.CourseListDto;
import woojjam.utrip.domains.course.dto.PlanDto;
import woojjam.utrip.domains.course.dto.UserCourseDetailDto;
import woojjam.utrip.domains.course.repository.UserCourseRepository;
import woojjam.utrip.domains.like.repository.VideoLikeRepository;
import woojjam.utrip.domains.place.domain.Place;
import woojjam.utrip.domains.place.dto.PlaceDto;
import woojjam.utrip.domains.place.repository.PlaceRepository;
import woojjam.utrip.domains.user.domain.User;
import woojjam.utrip.domains.user.repository.UserRepository;
import woojjam.utrip.domains.video.domain.Video;
import woojjam.utrip.domains.video.dto.VideoListDto;
import woojjam.utrip.domains.video.repository.VideoRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PlaceRepository placeRepository;
	private final VideoRepository videoRepository;
	private final UserCourseRepository userCourseRepository;
	private final VideoLikeRepository videoLikeRepository;

	@Transactional(readOnly = true)
	public ResponseEntity<?> findUserCourse(Long userId) {
		userRepository.findById(userId).orElseThrow(() -> new UserException(StatusCode.USER_NOT_FOUND));
		List<UserCourseDetailDto> userCourseDetailDtos = userCourseRepository.findDetailsByUserIdWithFetchJoin(userId);

		Map<String, List<UserCourseDetailDto>> groupedByCourse = userCourseDetailDtos.stream()
			.collect(Collectors.groupingBy(UserCourseDetailDto::getCourseName));

		List<CourseDto> courses = groupedByCourse.entrySet()
			.stream()
			.map(entry -> createCourseDto(entry.getKey(), entry.getValue()))
			.toList();

		return ResponseEntity.ok(
			SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), courses));
	}

	private CourseDto createCourseDto(String courseName, List<UserCourseDetailDto> details) {
		Long courseId = details.get(0).getCourseId();
		LocalDateTime createdAt = details.get(0).getCreatedAt();
		LocalDateTime updatedAt = details.get(0).getUpdatedAt();

		Map<Integer, List<UserCourseDetailDto>> groupedByDay = details.stream()
			.collect(Collectors.groupingBy(UserCourseDetailDto::getDay, Collectors.toList()));

		List<PlanDto> plans = groupedByDay.entrySet()
			.stream()
			.map(entry -> createPlanDto(entry.getKey(), entry.getValue()))
			.toList();

		return CourseDto.of(courseId, courseName, createdAt, updatedAt, plans);
	}

	private PlanDto createPlanDto(int day, List<UserCourseDetailDto> details) {
		List<PlaceDto> places = details.stream()
			.sorted(Comparator.comparingInt(UserCourseDetailDto::getOrder))
			.map(detail -> {
				Place place = Place.of(detail.getPlaceName(), detail.getDescription(), detail.getImage(),
					detail.getPosX(), detail.getPosY());
				return PlaceDto.of(detail.getOrder(), place);
			})
			.collect(Collectors.toList());

		return PlanDto.of(day, places);
	}

	@Transactional(readOnly = true)
	public ResponseEntity<?> getUserLikeVideo(Long userId) {
		List<Long> videoLikes = videoLikeRepository.findByUserId(userId)
			.stream()
			.map(videoLike -> videoLike.getVideo().getId())
			.toList();
		List<Video> findVideos = videoRepository.findByIdIn(videoLikes);
		List<VideoListDto> videoList = findVideos.stream().map(VideoListDto::from).toList();
		return ResponseEntity.ok(
			SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), videoList));
	}

	public ResponseEntity<?> deleteUserLikeVideo(Long userId, Long videoId) {
		videoLikeRepository.deleteByUserIdAndVideoId(userId, videoId);
		return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
	}

	public ResponseEntity<?> deleteUserCourse(Long userCourseId) {
		try {
			//            courseDetailRepository.deleteAllByUserCourseId(userCourseId);
			userCourseRepository.deleteById(userCourseId);
		} catch (Exception e) {
			throw new RuntimeException(StatusCode.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
	}

	public ResponseEntity<?> updateUserCourse(Long userId, Long userCourseId, CourseListDto courseListDto) {
		log.info("user id = {} , userCourse Id = {}", userId, userCourseId);
		// UserCourse 조회
		UserCourse userCourse = userCourseRepository.findById(userCourseId)
			.orElseThrow(() -> new NoSuchElementException(StatusCode.USER_COURSE_NOT_FOUND));

		log.info("User Course = {}", userCourse);
		// User 조회
		User user = userRepository.findById(userId).orElseThrow(() -> new UserException(StatusCode.USER_NOT_FOUND));

		//        courseDetailRepository.deleteAllByUserCourseId(userCourseId);
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

	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new UserException(StatusCode.USER_NOT_FOUND));
	}
}


