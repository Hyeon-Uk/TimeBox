package TIAB.timebox.repository;

import TIAB.timebox.entity.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    Member member, member1, member2;

    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .email("test@naver.com")
                .imgSrc("testImg")
                .kakaoId(123l)
                .build();
        member1 = Member.builder()
                .email("test1@naver.com")
                .imgSrc("testImg1")
                .kakaoId(1231l)
                .build();
        member2 = Member.builder()
                .email("test2@naver.com")
                .imgSrc("testImg2")
                .kakaoId(1232l)
                .build();

    }

    @Test
    public void saveUser() {
        //given

        //when
        userRepository.save(member);

        //then
        assertThat(userRepository.findByEmail(member.getEmail()).isPresent()).isEqualTo(true);
    }

    @Test
    public void findUserById() {
        //given
        userRepository.save(member);

        //when
        Member find = userRepository.findById(member.getId()).orElse(null);

        //then
        assertThat(find).isEqualTo(member);
    }

    @Test
    public void findByEmail() {
        //given
        userRepository.save(member);

        //when
        Member find = userRepository.findByEmail(member.getEmail()).orElse(null);

        //then
        assertThat(find).isEqualTo(member);
    }

    @Test
    public void findByKakaoId() {
        //given
        userRepository.save(member);

        //when
        Member find = userRepository.findByKakaoId(member.getKakaoId()).orElse(null);

        //then
        assertThat(find).isEqualTo(member);
    }

    @Test
    public void findAll() {
        int beforeSize = userRepository.findAll().size();

        //given && when && then
        userRepository.save(member);
        List<Member> members = userRepository.findAll();
        System.out.println(members.size());
        assertThat(members.size()).isEqualTo(beforeSize + 1);
        assertThat(members).contains(member);

        userRepository.save(member1);
        List<Member> users1 = userRepository.findAll();
        System.out.println(members.size());
        assertThat(users1.size()).isEqualTo(beforeSize + 2);
        assertThat(users1).contains(member);
        assertThat(users1).contains(member1);

        userRepository.save(member2);
        List<Member> users2 = userRepository.findAll();
        System.out.println(members.size());
        assertThat(users2.size()).isEqualTo(beforeSize + 3);
        assertThat(users2).contains(member);
        assertThat(users2).contains(member1);
        assertThat(users2).contains(member2);
    }

    @Test
    public void updateEmail() {
        //given
        Member saved = userRepository.save(member);

        //when
        saved.setEmail("changed@gmail.com");
        Member changedEmailMember = userRepository.save(saved);

        //then
        assertThat(changedEmailMember).isEqualTo(saved);
    }
}
