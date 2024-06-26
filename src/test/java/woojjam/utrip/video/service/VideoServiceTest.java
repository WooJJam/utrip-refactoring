package woojjam.utrip.video.service;

import woojjam.utrip.video.domain.Video;
import woojjam.utrip.video.repository.VideoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
class VideoServiceTest {

    @Autowired
    VideoRepository videoRepository;

//    @Test
//    public void 비디오_목록_조회() throws Exception {
//
//        for (long i = 1L; i < 13L; i++) {
//            System.out.println("i = " + i);
//            Video test = Video.builder()
//                    .likeCount(100)
//                    .title("Test Title" + i)
//                    .url("Test Url" + i)
//                    .tags(List.of("Test Tag" + i))
//                    .content("Test Content" + i)
//                    .build();
//            videoRepository.save(test);
//        }
//
//        List<Video> findVideos = videoRepository.findTop12ByOrderByLikeCountDesc();
//        int size = findVideos.size();
//        Assertions.assertThat(size).isEqualTo(12);
//    }

    @Test
    public void 비디오_상세_정보_조회() throws Exception {

        Video test = Video.builder()
                .title("Test Title")
                .content("Test content")
                .build();

        videoRepository.save(test);

        Optional<Video> findVideo = videoRepository.findById(test.getId());
        Assertions.assertThat(findVideo).isPresent();
        Assertions.assertThat(test.getId()).isEqualTo(findVideo.get().getId());
        Assertions.assertThat(test.getTitle()).isEqualTo(findVideo.get().getTitle());
        Assertions.assertThat(test.getContent()).isEqualTo(findVideo.get().getContent());
    }

}
