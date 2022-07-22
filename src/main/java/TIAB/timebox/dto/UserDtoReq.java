package TIAB.timebox.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDtoReq {
    private Long kakaoId;
    private String email;
    private String imgSrc;
}
