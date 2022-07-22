package TIAB.timebox.dto;

import TIAB.timebox.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDtoRes {
    private Long id;
    private String email;
    private long kakaoId;
    private User user;
}
