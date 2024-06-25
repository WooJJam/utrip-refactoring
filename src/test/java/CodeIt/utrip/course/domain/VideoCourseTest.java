package CodeIt.utrip.course.domain;

import CodeIt.utrip.course.repository.VideoCourseRepository;
import CodeIt.utrip.place.repository.PlaceRepository;
import CodeIt.utrip.video.domain.Video;
import CodeIt.utrip.video.repository.VideoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@Transactional
class VideoCourseTest {

    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private VideoCourseRepository videoCourseRepository;

    @Test
    public void 코스_저장() throws Exception {
        //given

        Video video = Video.builder()
                .videoUrl("testURL")
                .tags(Arrays.asList("testTag"))
                .title("testTitle")
                .content("testContent")
                .likeCount(100)
                .build();

        videoRepository.save(video);

        VideoCourse videoCourse = VideoCourse.builder()
                .places("1,2")
                .video(video)
                .build();
        //when
        videoCourseRepository.save(videoCourse);

        //then
        Optional<VideoCourse> findCourse = videoCourseRepository.findById(videoCourse.getId());
        Assertions.assertThat(findCourse.get().getPlaces()).isEqualTo("1,2");
    }

//    @Test
//    public void 코스_검색() throws Exception {
//        //given
//
//        Video video = Video.builder()
//                .url("testURL")
//                .tag("testTag")
//                .title("testTitle")
//                .content("testContent")
//                .likeCount(100)
//                .build();
//
//        videoRepository.save(video);
//
//        VideoCourse course = VideoCourse.builder()
//                .dayNum(1)
//                .places("1,2")
//                .video(video)
//                .build();
//        //when
//        videoCourseRepository.save(course);
//
//        Place place = Place.builder()
//                .name("place_name 1")
//                .posX(100.21)
//                .posY(28.21)
//                .build();
//
//        Place place2 = Place.builder().name("place_name 2")
//                .posX(22.11)
//                .posY(54.21)
//                .build();
//
//        placeRepository.save(place);
//        placeRepository.save(place2);
//
//        //then
//        Optional<VideoCourse> findCourse = videoCourseRepository.findById(course.getId());
//
//        List<String> placeStrings = List.of(findCourse.get().getPlaces().split(","));
//
//        List<Long> places = placeStrings.stream()
//                .map(Long::valueOf)  // 각 String 값을 Long으로 변환
//                .toList();
//
//        Long placeId = places.get(0);
//        Optional<Place> findPlace = placeRepository.findById(placeId);
//        System.out.println("findPlace.get(). = " + findPlace.get().);
//    }
}