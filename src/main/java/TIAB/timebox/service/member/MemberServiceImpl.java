package TIAB.timebox.service.member;

import TIAB.timebox.dto.MemberDtoReq;
import TIAB.timebox.dto.MemberDtoRes;
import TIAB.timebox.entity.member.Member;
import TIAB.timebox.exception.UserNotFoundException;
import TIAB.timebox.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final UserRepository userRepository;

    @Override
    public MemberDtoRes save(MemberDtoReq dto) {
        Member member = userRepository.findByKakaoId(dto.getKakaoId()).orElse(dtoToEntity(dto));
        return entityToDto(userRepository.save(member));
    }

    //Exception 처리하기
    @Override
    public MemberDtoRes getMember(long id) {
        Member member = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException());
        return entityToDto(member);
    }

    @Override
    public MemberDtoRes findByKakaoId(long kakaoId) {
        Member member = userRepository.findByKakaoId(kakaoId).orElseThrow(() -> new UserNotFoundException());
        return entityToDto(member);
    }

    @Override
    public List<MemberDtoRes> getAllMembers() {
        return userRepository.findAll().stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());
    }
}
