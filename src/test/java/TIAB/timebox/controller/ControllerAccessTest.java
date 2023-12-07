package TIAB.timebox.controller;

import TIAB.timebox.dto.MemberDtoReq;
import TIAB.timebox.dto.MemberDtoRes;
import TIAB.timebox.dto.MessageDtoReq;
import TIAB.timebox.dto.MessageDtoRes;
import TIAB.timebox.exception.CanNotAccessException;
import TIAB.timebox.exception.MessageNotFoundException;
import TIAB.timebox.exception.NotPassedDeadlineException;
import TIAB.timebox.exception.UserNotFoundException;
import TIAB.timebox.service.member.MemberService;
import TIAB.timebox.service.message.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ControllerAccessTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberService memberService;
    @Autowired
    private MessageService messageService;

    @Nested
    @DisplayName("with Anonymous User")
    class CanNotAccess {
        @Test
        @DisplayName("홈 접근불가")
        @WithAnonymousUser
        public void canNotAccessHome() throws Exception {
            mvc.perform(get("/"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection());
        }

        @Test
        @DisplayName("로그인은 접근 가능")
        @WithAnonymousUser
        public void canAccessLogin() throws Exception {
            mvc.perform(get("/auth/login"))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("메세지 작성 접근불가능")
        @WithAnonymousUser
        public void canNotAccessMake() throws Exception {
            mvc.perform(get("/message"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection());
        }

        @Test
        @DisplayName("메세지 전송 불가능")
        @WithAnonymousUser
        public void canNotPostMessage() throws Exception {
            mvc.perform(post("/message")
                            .param("deadline", String.valueOf(new Date())))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection());
        }

        @Test
        @DisplayName("메세지 조회 불가능")
        @WithAnonymousUser
        public void canNotGetMessage() throws Exception {
            mvc.perform(get("/message/1"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection());
        }

    }

    @Nested
    @DisplayName("With OAuth2 User")
    class CanAccess {
        MemberDtoRes memberDtoRes1, memberDtoRes2;
        MessageDtoReq messageDtoReq1, messageDtoReq2, messageDtoReq3;
        MessageDtoRes messageDtoRes1, messageDtoRes2, messageDtoRes3;
        SecurityMockMvcRequestPostProcessors.OAuth2LoginRequestPostProcessor user1, user2, undefinedUser;

        private MockMultipartFile getMockMultifile(String filename, String contentType, String path) {
            return new MockMultipartFile(filename, filename + "." + contentType, contentType, path.getBytes());
        }

        @BeforeEach
        public void init() throws IOException {
            MockMultipartFile mockMultipartFile = getMockMultifile("test", "test", "testpath");
            //user1 생성
            MemberDtoReq memberDtoReq1 = MemberDtoReq.builder()
                    .kakaoId(123l)
                    .email("test123@naver.com")
                    .imgSrc(null)
                    .build();
            memberDtoRes1 = memberService.save(memberDtoReq1);
            //user2 생성
            MemberDtoReq memberDtoReq2 = MemberDtoReq.builder()
                    .kakaoId(312l)
                    .email("test321@naver.com")
                    .imgSrc(null)
                    .build();
            memberDtoRes2 = memberService.save(memberDtoReq2);

            //user1의 message1 생성
            messageDtoReq1 = MessageDtoReq.builder()
                    .content(mockMultipartFile)
                    .deadline(new Date())
                    .member(memberDtoRes1.getMember())
                    .filename("123")
                    .fileUrl("123")
                    .height(15)
                    .width(15)
                    .build();

            //user1의 message2 생성
            messageDtoReq2 = MessageDtoReq.builder()
                    .content(mockMultipartFile)
                    .deadline(new Date(new Date().getTime() + 6000))
                    .member(memberDtoRes1.getMember())
                    .filename("123")
                    .fileUrl("123")
                    .height(15)
                    .width(15)
                    .build();

            //user2의 message1 생성
            messageDtoReq3 = MessageDtoReq.builder()
                    .content(mockMultipartFile)
                    .deadline(new Date())
                    .member(memberDtoRes2.getMember())
                    .filename("123")
                    .fileUrl("123")
                    .height(15)
                    .width(15)
                    .build();

            messageDtoRes1 = messageService.save(memberDtoRes1.getId(), messageDtoReq1);
            messageDtoRes2 = messageService.save(memberDtoRes1.getId(), messageDtoReq2);
            messageDtoRes3 = messageService.save(memberDtoRes2.getId(), messageDtoReq3);


            //user1 oauth2 생성
            user1 = oauth2Login()
                    .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                    .attributes(attr -> {
                        attr.put("id", memberDtoRes1.getId());
                    });
            //user2 oauth2 생성
            user2 = oauth2Login()
                    .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                    .attributes(attr -> {
                        attr.put("id", memberDtoRes2.getId());
                    });
            //없는 유저 생성
            undefinedUser = oauth2Login()
                    .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                    .attributes(attr -> {
                        attr.put("id", 123123291l);
                    });

        }

        @Test
        @DisplayName("홈 접근 가능")
        public void canAccess() throws Exception {
            mvc.perform(get("/").with(user1))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("메세지 작성 접근가능")
        public void canAccessMake() throws Exception {
            mvc.perform(get("/message").with(user1))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("시간이 만료된 메세지 조회가능")
        public void canGetMessageExpiredDeadline() throws Exception {
            mvc.perform(get("/message/" + messageDtoRes1.getId()).with(user1))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("시간이 만료되지 않은 메세지 조회 불가능")
        public void canNotGetMessageNotExpiredDeadline() throws Exception {
            mvc.perform(get("/message/" + messageDtoRes2.getId()).with(user1))
                    .andDo(print())
                    .andExpect(result -> {
                        assertThat(result.getResolvedException().getClass()).isAssignableFrom(NotPassedDeadlineException.class);
                    })
                    .andExpect(status().is3xxRedirection());
        }

        @Test
        @DisplayName("다른 사람의 메세지 조회 불가능")
        public void canNotAccessOtherMessage() throws Exception {
            mvc.perform(get("/message/" + messageDtoRes3.getId()).with(user1))
                    .andDo(print())
                    .andExpect(result -> {
                        assertThat(result.getResolvedException().getClass()).isAssignableFrom(CanNotAccessException.class);
                    })
                    .andExpect(status().is3xxRedirection());
        }

        @Test
        @DisplayName("없는 메세지 조회 불가능")
        public void canNotAccessNotExistMessage() throws Exception {
            mvc.perform(get("/message/" + (messageDtoRes3.getId() + 1)).with(user1))
                    .andDo(print())
                    .andExpect(result -> {
                        assertThat(result.getResolvedException().getClass()).isAssignableFrom(MessageNotFoundException.class);
                    })
                    .andExpect(status().is3xxRedirection());
        }

        @Test
        @DisplayName("유저가 메세지 전송")
        public void userPostMessage() throws Exception {
            String formatted = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            mvc.perform(
                            multipart("/message")
                                    .file("content", getMockMultifile("aa", "aa", "Aa").getBytes())
                                    .param("height", "15")
                                    .param("width", "15")
                                    .param("deadline", formatted)
                                    .with(request -> {
                                        request.setMethod("POST");
                                        return request;
                                    }).with(user1)
                    )
                    .andDo(print())
                    .andExpect(status().is3xxRedirection());
        }

        @Test
        @DisplayName("없는 유저가 메세지를 만들기위해 접근")
        public void undefinedUserAccess() throws Exception {
            String formatted = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            mvc.perform(
                            multipart("/message")
                                    .file("content", getMockMultifile("aa", "aa", "aa").getBytes())
                                    .param("width", "15")
                                    .param("height", "15")
                                    .param("deadline", formatted)
                                    .with(request -> {
                                        request.setMethod("POST");
                                        return request;
                                    }).with(undefinedUser))
                    .andDo(print())
                    .andExpect(result -> {
                        assertThat(result.getResolvedException().getClass()).isAssignableFrom(UserNotFoundException.class);
                    })
                    .andExpect(status().is3xxRedirection());
        }

        @Test
        @DisplayName("데드라인을 입력안했을시에 redirect")
        public void notInputDeadline() throws Exception {
            mvc.perform(
                            multipart("/message")
                                    .file("content", getMockMultifile("aa", "aa", "aa").getBytes())
                                    .param("width", "15")
                                    .param("height", "15")
                                    .with(request -> {
                                        request.setMethod("POST");
                                        return request;
                                    }).with(undefinedUser))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection());
        }
    }
}
