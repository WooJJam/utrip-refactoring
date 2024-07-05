package woojjam.utrip.admin;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import woojjam.utrip.like.repository.VideoLikeRepository;
import woojjam.utrip.place.domain.Place;
import woojjam.utrip.place.repository.PlaceRepository;
import woojjam.utrip.review.repository.ReviewRepository;
import woojjam.utrip.video.domain.Video;
import woojjam.utrip.video.repository.VideoRepository;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class AdminService {

	private final ReviewRepository reviewRepository;
	private final VideoRepository videoRepository;
	private final PlaceRepository placeRepository;
	private final VideoLikeRepository videoLikeRepository;

	public void saveVideoAndVideoCourse(SaveVideoRequest saveVideoRequest) {

		Video video = Video.builder()
			.content(saveVideoRequest.getContent())
			.title(saveVideoRequest.getTitle())
			.tags(saveVideoRequest.getTag())
			.videoUrl(saveVideoRequest.getVideoUrl())
			.imageUrl(saveVideoRequest.getImageUrl())
			.build();
		videoRepository.save(video);

		String placeIds = saveVideoRequest.getPlace().stream().map(p -> {
			Optional<Place> findPlace = placeRepository.findByPxAndPy(p.getPosX(), p.getPosY());

			if (findPlace.isEmpty()) {
				Place place = Place.builder()
					.description(p.getDescription())
					.img(p.getImg())
					.name(p.getName())
					.px(p.getPosX())
					.py(p.getPosY())
					.build();
				return placeRepository.save(place).getId().toString();
			} else {
				return findPlace.get().getId().toString();
			}
		}).collect(Collectors.joining(","));

		//        VideoCourse videoCourse = VideoCourse.builder()
		//                .video(video).build();
		//                .places(placeIds).build();

		//        videoCourseRepository.save(videoCourse);
	}

	public void deleteVideo(Long videoId) {
		reviewRepository.deleteByVideoId(videoId);
		videoLikeRepository.deleteByVideoId(videoId);
		videoRepository.deleteById(videoId);
	}
}
