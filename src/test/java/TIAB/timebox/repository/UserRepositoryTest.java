package TIAB.timebox.repository;

import TIAB.timebox.entity.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser(){
        User user=new User();
        user.setEmail("test123");
        user.setImgSrc("testsrc");
        user.setKakaoId(1234l);

        userRepository.save(user);
        assertThat(userRepository.findByEmail(user.getEmail()).isPresent()).isEqualTo(true);
    }
}
