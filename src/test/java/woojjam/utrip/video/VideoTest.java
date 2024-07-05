package woojjam.utrip.video;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import woojjam.utrip.video.domain.Video;

@SpringBootTest
@Transactional
public class VideoTest {

	@PersistenceContext
	private EntityManager em;

	@Test
	public void 비디오_저장() throws Exception {
		//given
		Video video = Video.builder()
			.build();
		em.persist(video);

		//when
		Video find = em.find(Video.class, video.getId());

		//then
		Assertions.assertThat(find).isEqualTo(video);
	}
}
