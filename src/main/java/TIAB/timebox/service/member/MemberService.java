package TIAB.timebox.service.member;

import TIAB.timebox.dto.MemberDtoReq;
import TIAB.timebox.dto.MemberDtoRes;
import TIAB.timebox.entity.member.Member;

import java.util.List;

public interface MemberService {
    public MemberDtoRes save(MemberDtoReq dto);

    public MemberDtoRes getMember(long id);

    public MemberDtoRes findByKakaoId(long kakaoId);

    public List<MemberDtoRes> getAllMembers();

    default Member dtoToEntity(MemberDtoReq dto) {
        return Member.builder()
                .email(dto.getEmail())
                .kakaoId(dto.getKakaoId())
                .imgSrc(dto.getImgSrc())
                .build();
    }

    default MemberDtoRes entityToDto(Member entity) {
        return MemberDtoRes.builder()
                .email(entity.getEmail())
                .kakaoId(entity.getKakaoId())
                .id(entity.getId())
                .member(entity)
                .build();
    }
}
