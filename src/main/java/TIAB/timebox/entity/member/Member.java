package TIAB.timebox.entity.member;

import TIAB.timebox.entity.BaseEntity;
import TIAB.timebox.entity.message.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "social_id")
    private String socialId;
    @Column(name = "social_provider")
    private String socialProvider;
    @Column(name = "email")
    private String email;
    @Column(name = "profile_img")
    private String profileImg;

    @Builder
    public Member(String socialId, String socialProvider, String email, String profileImg) {
        this.socialProvider = socialProvider;
        this.socialId = socialId;
        this.email = email;
        this.profileImg = profileImg;
    }

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    public void updateProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
