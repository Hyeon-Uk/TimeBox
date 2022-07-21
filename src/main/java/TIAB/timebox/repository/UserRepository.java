package TIAB.timebox.repository;

import TIAB.timebox.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByKakaoId(Long kakao_id);
}
