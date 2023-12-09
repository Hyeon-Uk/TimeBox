package TIAB.timebox.service.member;

import TIAB.timebox.dto.MemberDTO;
import TIAB.timebox.exception.UserNotFoundException;
import TIAB.timebox.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public MemberDTO.Response findById(MemberDTO.Request req) {
        return MemberDTO.entityToDto(memberRepository.findById(req.getId())
                .orElseThrow(() -> new UserNotFoundException()));
    }
}
