package TIAB.timebox.service.member;

import TIAB.timebox.dto.MemberDTO;

public interface MemberService {
    MemberDTO.Response findById(MemberDTO.Request req);
}
