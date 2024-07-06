package woojjam.utrip.domains.video.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.reponse.StatusCode;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.domains.video.dto.VideoListDto;
import woojjam.utrip.domains.video.service.VideoService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/video")
public class VideoController {

	private final VideoService videoService;
	// private final JwtUtils jwtUtils;

	@GetMapping
	public ResponseEntity<?> getVideoList() {
		List<VideoListDto> videoList = videoService.getAllVideos();
		return ResponseEntity.ok(
			SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), videoList));
	}

	@GetMapping("/tag/{tag}")
	public ResponseEntity<?> getVideosByTag(@PathVariable String tag, @RequestParam int page, @RequestParam int size) {
		List<VideoListDto> videos = videoService.getVideosByTag(tag, page, size);
		return ResponseEntity.ok(
			SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(), videos));
	}

	@GetMapping("/{video_id}")
	public ResponseEntity<?> getVideoDetailInfo(@PathVariable("video_id") Long videoId) {
		return videoService.getVideoDetailInfo(videoId);
	}

	@PostMapping("/{video_id}/likes")
	public ResponseEntity<?> likeVideo(@PathVariable("video_id") Long videoId, HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		// String token = jwtUtils.splitBearerToken(bearerToken);
		// String email = (String)jwtUtils.getClaims(token).get("email");

		// videoService.likeVideo(videoId, email);
		return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
	}

	@GetMapping("/top-liked")
	public ResponseEntity<?> getVideoLikesFive(@RequestParam(defaultValue = "5") int limit) {
		return ResponseEntity.ok(
			SuccessResponse.of(
				StatusCode.SUCCESS.getCode(),
				StatusCode.SUCCESS.getMessage(),
				videoService.getTopLikedVideo(limit)
			)
		);
	}
}
