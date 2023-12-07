package TIAB.timebox.service;


import TIAB.timebox.dto.MemberDtoReq;
import TIAB.timebox.dto.MemberDtoRes;
import TIAB.timebox.entity.member.Member;
import TIAB.timebox.exception.UserNotFoundException;
import TIAB.timebox.repository.UserRepository;
import TIAB.timebox.service.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceImplTest {
    @InjectMocks
    private MemberServiceImpl userService; //

    @Mock
    private UserRepository userRepository;

    MemberDtoReq memberDtoReq1, memberDtoReq2;
    MemberDtoRes memberDtoRes1, memberDtoRes2;
    Member beforeEntity1, afterEntity1, beforeEntity2, afterEntity2;

    @BeforeEach
    public void init() {
        memberDtoReq1 = MemberDtoReq.builder()
                .kakaoId(1l)
                .imgSrc("imgsrc")
                .email("test123@gmail.com")
                .build();

        memberDtoReq2 = MemberDtoReq.builder()
                .kakaoId(2l)
                .imgSrc("imgsrc1")
                .email("test1234@gmail.com")
                .build();

        beforeEntity1 = userService.dtoToEntity(memberDtoReq1);
        afterEntity1 = userService.dtoToEntity(memberDtoReq1);
        afterEntity1.setId(1l);

        beforeEntity2 = userService.dtoToEntity(memberDtoReq2);
        afterEntity2 = userService.dtoToEntity(memberDtoReq2);
        afterEntity2.setId(2l);

        memberDtoRes1 = userService.entityToDto(afterEntity1);
        memberDtoRes2 = userService.entityToDto(afterEntity2);

    }

    //객체 두개를 비교해주는 메소드
    public void assertThatDtoEqualToDto(MemberDtoRes dto1, MemberDtoRes dto2) {
        assertThat(dto1.getKakaoId()).isEqualTo(dto2.getKakaoId());
        assertThat(dto1.getMember()).isEqualTo(dto2.getMember());
        assertThat(dto1.getId()).isEqualTo(dto2.getId());
        assertThat(dto1.getEmail()).isEqualTo(dto2.getEmail());
    }

    @Nested
    @DisplayName("Success Case")
    class Success {
        @Test
        public void mockUserRepository() {
            //given
            List<Member> members = new ArrayList<>();
            members.add(afterEntity1);
            when(userRepository.findAll()).thenReturn(members);

            //when
            List<MemberDtoRes> actual = userService.getAllMembers();

            //then
            assertThat(actual.size()).isEqualTo(1);
            assertThatDtoEqualToDto(actual.get(0), memberDtoRes1);
        }

        @Test
        public void saveTest_새로운사용자() {
            //given
            when(userRepository.save(any(Member.class))).thenReturn(afterEntity1);
            when(userRepository.findByKakaoId(anyLong())).thenReturn(Optional.ofNullable(afterEntity1));

            //when
            MemberDtoRes actual = userService.save(memberDtoReq1);

            //then
            assertThatDtoEqualToDto(actual, memberDtoRes1);
        }

        @Test
        public void saveTest_기존사용자_이미지src변경() {
            //given
            String changedSrc = "changedSrc";
            memberDtoReq1.setImgSrc(changedSrc);
            afterEntity1.setImgSrc(changedSrc);
            memberDtoRes1.setMember(afterEntity1);
            when(userRepository.save(any(Member.class))).thenReturn(afterEntity1);

            //when
            MemberDtoRes actual = userService.save(memberDtoReq1);

            //then
            assertThatDtoEqualToDto(actual, memberDtoRes1);
        }

        @Test
        public void findAllTest() {
            //given
            List<Member> memberList = new ArrayList<>();
            memberList.add(afterEntity1);
            memberList.add(afterEntity2);
            when(userRepository.findAll()).thenReturn(memberList);
            List<MemberDtoRes> expects = new ArrayList<>();
            expects.add(memberDtoRes1);
            expects.add(memberDtoRes2);

            //when
            List<MemberDtoRes> actual = userService.getAllMembers();

            //then
            IntStream.range(0, actual.size()).forEach(i -> {
                assertThatDtoEqualToDto(actual.get(i), expects.get(i));
            });
        }

        @Test
        public void findByKakaoIdTest() {
            //given
            when(userRepository.findByKakaoId(anyLong())).thenReturn(Optional.ofNullable(afterEntity1));

            //when
            MemberDtoRes actual = userService.findByKakaoId(memberDtoReq1.getKakaoId());

            //then
            assertThatDtoEqualToDto(actual, memberDtoRes1);
        }

        @Test
        public void findByIdTest() {
            //given
            when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(afterEntity1));

            //when
            MemberDtoRes actual = userService.getMember(1l);

            //then
            assertThatDtoEqualToDto(actual, memberDtoRes1);
        }
    }

    @Nested
    @DisplayName("Failed Case")
    class Failed {
        @Test
        public void findByKakaoIdExceptionTest() {
            //given
            when(userRepository.findByKakaoId(anyLong())).thenReturn(Optional.ofNullable(null));

            //when & then
            assertThrows(UserNotFoundException.class, () -> userService.findByKakaoId(3l));
        }

        @Test
        public void findByIdExceptionTest() {
            //given
            when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));

            //when & then
            assertThrows(UserNotFoundException.class, () -> userService.getMember(1l));
        }
    }
}
