package TIAB.timebox.dto;

import TIAB.timebox.entity.member.Member;
import TIAB.timebox.entity.message.Message;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class MemberDTO {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private Long id;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String profileImg;
        @Builder.Default
        private List<Message> messages = new ArrayList<>();
    }

    public static MemberDTO.Response entityToDto(Member entity) {
        return Response.builder()
                .profileImg(entity.getProfileImg())
                .messages(entity.getMessages())
                .build();
    }
}
