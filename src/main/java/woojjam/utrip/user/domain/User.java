package woojjam.utrip.user.domain;

import woojjam.utrip.course.domain.UserCourse;
import woojjam.utrip.like.domain.ReviewLike;
import woojjam.utrip.like.domain.VideoLike;
import woojjam.utrip.review.domain.Review;
import woojjam.utrip.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String nickname;
    private String email;
    private String password;
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<ReviewLike> reviewLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<VideoLike> videoLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @Builder.Default
    private List<UserCourse> userCourses = new ArrayList<>();

    public void createUser(String nickname, String email, String password, String refreshToken) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.refreshToken = refreshToken;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void changePassword(String password) {
        this.password = password;
    }
}
