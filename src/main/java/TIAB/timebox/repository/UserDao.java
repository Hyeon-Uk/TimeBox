package TIAB.timebox.repository;

import TIAB.timebox.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);
    public Optional<User> findByKakaoId(Long kakao_id);
}
