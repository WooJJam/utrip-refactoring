package woojjam.utrip.video.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.common.exception.NoSuchElementException;
import woojjam.utrip.common.exception.UserException;
import woojjam.utrip.common.reponse.StatusCode;
import woojjam.utrip.common.reponse.SuccessResponse;
import woojjam.utrip.like.domain.VideoLike;
import woojjam.utrip.like.repository.VideoLikeRepository;
import woojjam.utrip.user.domain.User;
import woojjam.utrip.user.repository.UserRepository;
import woojjam.utrip.video.domain.Video;
import woojjam.utrip.video.dto.VideoInfoDto;
import woojjam.utrip.video.dto.VideoListDto;
import woojjam.utrip.video.repository.VideoRepository;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class VideoService {

	private final VideoRepository videoRepository;
	private final UserRepository userRepository;
	private final VideoLikeRepository videoLikeRepository;

	@Transactional(readOnly = true)
	public List<VideoListDto> getAllVideos() {
		List<Video> videos = videoRepository.findAll();

		if (videos.isEmpty()) {
			throw new NoSuchElementException(StatusCode.VIDEO_NOT_FOUND);
		}

		return videos.stream()
			.map(VideoListDto::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public List<VideoListDto> getVideosByTag(String tag, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Video> videos = videoRepository.findByTagsContaining(tag, pageable);

		if (videos.isEmpty()) {
			throw new NoSuchElementException(StatusCode.VIDEO_NOT_FOUND);
		}

		return videos.stream()
			.map(VideoListDto::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public ResponseEntity<?> getVideoDetailInfo(Long videoId) {
		Video video = videoRepository.findById(videoId)
			.orElseThrow(() -> new NoSuchElementException(StatusCode.VIDEO_NOT_FOUND));
		return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage(),
			VideoInfoDto.fromEntity(video)));
	}

	public ResponseEntity<?> likeVideo(Long videoId, String email) {
		Video video = findVideoById(videoId);
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UserException(StatusCode.USER_NOT_FOUND));

		boolean alreadyLiked = videoLikeRepository.existsByVideoIdAndUserId(video.getId(), user.getId());
		if (alreadyLiked) {
			return ResponseEntity.badRequest()
				.body(SuccessResponse.of(StatusCode.ALREADY_LIKED.getCode(), StatusCode.ALREADY_LIKED.getMessage()));
		}

		VideoLike videoLike = VideoLike.from(user, video);
		video.getVideoLikes().add(videoLike);

		video.setLikecount(video.getLikeCount() + 1);
		videoRepository.save(video);

		log.info("Video ID = {}, Video like: {}", videoId, video.getLikeCount());

		return ResponseEntity.ok(SuccessResponse.of(StatusCode.SUCCESS.getCode(), StatusCode.SUCCESS.getMessage()));
	}

	private Video findVideoById(Long videoId) {
		return videoRepository.findById(videoId)
			.orElseThrow(() -> new NoSuchElementException(StatusCode.VIDEO_NOT_FOUND));
	}

	public List<VideoListDto> getTopLikedVideo(int limit) {
		List<Video> topLikedVideos = videoRepository.findTopByOrderByLikeCountDesc(PageRequest.of(0, limit));
		return topLikedVideos.stream()
			.map(VideoListDto::from)
			.toList();
	}
}
