package CodeIt.utrip.admin;

import CodeIt.utrip.common.reponse.StatusCode;
import CodeIt.utrip.common.reponse.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<SuccessResponse<StatusCode>> saveVideoAndVideoCourse(@RequestBody SaveVideoRequest saveVideoRequest) {
        adminService.saveVideoAndVideoCourse(saveVideoRequest);
        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }

    @DeleteMapping("/admin/video/{video_id}")
    public ResponseEntity<?> deleteVideo(@PathVariable("video_id") Long videoId) {
        adminService.deleteVideo(videoId);
        return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
    }
}
