package woojjam.utrip.domains.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.exception.StatusCode;
import woojjam.utrip.common.reponse.SuccessResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AdminController {

	private final AdminService adminService;

	@GetMapping
	public ResponseEntity<?> ok() {
		return ResponseEntity.ok(null);
	}

	@PostMapping("/admin/video")
	public ResponseEntity<?> saveVideoAndVideoCourse(@RequestBody SaveVideoRequest saveVideoRequest) {
		adminService.saveVideoAndVideoCourse(saveVideoRequest);
		return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS));
	}

	@DeleteMapping("/admin/video/{video_id}")
	public ResponseEntity<?> deleteVideo(@PathVariable("video_id") Long videoId) {
		adminService.deleteVideo(videoId);
		return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS));
	}
}
