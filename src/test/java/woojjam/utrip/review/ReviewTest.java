//package CodeIt.Ytrip.review;
//
//import CodeIt.Ytrip.review.domain.Review;
//import CodeIt.Ytrip.user.domain.User;
//import CodeIt.Ytrip.video.domain.Video;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//class ReviewTest {
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Test
//    public void 댓글_생성() throws Exception {
//
//        // given
//        User user = new User();
//        user.createUser("테스터", "test@test.com", "1234", null);
//        em.persist(user);
//
//        Video video = Video.of("Test Video Title", "Test Video Content", "url", 10, "Test Video Tag");
//        em.persist(video);
//
//        Review review = Review.of(user, video, "Test Review Content");
//        em.persist(review);
//
//        //when
//        Review findReview = em.find(Review.class, review.getId());
//
//        //then
//        Assertions.assertThat(findReview).isEqualTo(review);
//    }
//}
