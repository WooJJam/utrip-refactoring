package CodeIt.utrip.course.controller;

import CodeIt.utrip.common.JwtUtils;
import CodeIt.utrip.course.dto.CourseListDto;
import CodeIt.utrip.course.service.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;
    private final JwtUtils jwtUtils;

    @PostMapping
    public ResponseEntity<?> postUserCourse(@RequestBody CourseListDto courseListDto, HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        String token = jwtUtils.splitBearerToken(bearerToken);
        String email = (String) jwtUtils.getClaims(token).get("email");
        return courseService.postUserCourse(courseListDto, email);
    }
    @GetMapping("/{video_id}")
    public ResponseEntity<?> getVideoCourse(@PathVariable("video_id") Long videoId) {
        return courseService.getVideoCourse(videoId);
    }

}
