package TIAB.timebox.entity.user;

import TIAB.timebox.entity.BaseEntity;
import TIAB.timebox.entity.message.Message;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="kakao_id")
    private Long kakaoId;
    @Column(name="email")
    private String email;
    @Column(name="img_src")
    private String imgSrc;

    @Builder
    public User(Long kakaoId, String email, String imgSrc) {
        this.kakaoId = kakaoId;
        this.email = email;
        this.imgSrc = imgSrc;
    }

    @OneToMany(mappedBy = "user")
    private List<Message> messages=new ArrayList<>();
}
