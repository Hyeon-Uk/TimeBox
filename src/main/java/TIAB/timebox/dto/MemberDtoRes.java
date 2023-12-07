package TIAB.timebox.dto;

import TIAB.timebox.entity.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MemberDtoRes {
    private Long id;
    private String email;
    private long kakaoId;
    private Member member;
}
