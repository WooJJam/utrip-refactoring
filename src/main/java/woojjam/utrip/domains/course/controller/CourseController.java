package woojjam.utrip.domains.course.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.exception.StatusCode;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.domains.course.dto.CourseListDto;
import woojjam.utrip.domains.course.service.CourseService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseController {

	private final CourseService courseService;
	// private final JwtUtils jwtUtils;

	@PostMapping
	public ResponseEntity<?> postUserCourse(@RequestBody CourseListDto courseListDto, HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		// String token = jwtUtils.splitBearerToken(bearerToken);
		// String email = (String)jwtUtils.getClaims(token).get("email");
		// courseService.postUserCourse(courseListDto, email);
		return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS));
	}

	@GetMapping("/{video_id}")
	public ResponseEntity<?> getVideoCourse(@PathVariable("video_id") Long videoId) {
		return courseService.getVideoCourse(videoId);
	}

}
