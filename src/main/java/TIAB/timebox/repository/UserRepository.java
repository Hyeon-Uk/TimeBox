package TIAB.timebox.repository;

import TIAB.timebox.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByEmail(String email);

    public Optional<Member> findByKakaoId(Long kakao_id);
}
