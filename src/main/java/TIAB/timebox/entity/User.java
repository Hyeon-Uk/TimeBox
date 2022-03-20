package TIAB.timebox.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="user")
public class User {

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

    @OneToMany(mappedBy = "user")
    private List<Message> messages=new ArrayList<>();
}
