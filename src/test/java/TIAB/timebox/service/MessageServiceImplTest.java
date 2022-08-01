package TIAB.timebox.service;

import TIAB.timebox.dto.FileServiceDtoRes;
import TIAB.timebox.dto.MessageDtoReq;
import TIAB.timebox.dto.MessageDtoRes;
import TIAB.timebox.entity.message.Message;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.exception.MessageNotFoundException;
import TIAB.timebox.exception.UserNotFoundException;
import TIAB.timebox.repository.MessageRepository;
import TIAB.timebox.repository.UserRepository;
import TIAB.timebox.service.file.FileService;
import TIAB.timebox.service.message.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceImplTest {

    @InjectMocks
    private MessageServiceImpl messageService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private FileService fileService;

    User user1,user2;

    MessageDtoReq yesterdayDtoReq,todayDtoReq,tomorrowDtoReq;
    Message yesterdayMessage,todayMessage,tomorrowMessage,yesterdayMessageAfter,todayMessageAfter,tomorrowMessageAfter;
    MessageDtoRes yesterdayDtoRes,todayDtoRes,tomorrowDtoRes;
    String filename1,filename2,filename3;
    String filepath,filepath1,filepath2,filepath3,contentType;
    Date yesterday,today,tomorrow;

    private MockMultipartFile getMockMultifile(String filename,String contentType,String path){
        return new MockMultipartFile(filename,filename+"."+contentType,contentType,path.getBytes());
    }

    @BeforeEach
    public void init() {
        filename1="file1";
        filename2="file2";
        filename3="file3";
        contentType="png";
        filepath="/messagebox";
        filepath1=filepath+"/"+filename1+"."+contentType;
        filepath2=filepath+"/"+filename2+"."+contentType;
        filepath3=filepath+"/"+filename3+"."+contentType;

        yesterday=new Date(new Date().getTime()-(1000 * 60 * 60 * 24 * 1));
        today=new Date();
        tomorrow=new Date(new Date().getTime()+(1000 * 60 * 60 * 24 * 1));

        user1=User.builder()
                .email("test1@gmail.com")
                .kakaoId(1l)
                .imgSrc("testImgSrc")
                .build();
        user1.setId(1l);

        user2=User.builder()
                .email("test2@gmail.com")
                .kakaoId(2l)
                .imgSrc("test2ImgSrc")
                .build();
        user2.setId(2l);

        yesterdayDtoReq=MessageDtoReq.builder()
                .user(user1)
                .content(getMockMultifile(filename1,contentType,filepath1))
                .deadline(yesterday)
                .height(10)
                .width(10)
                .build();

        todayDtoReq=MessageDtoReq.builder()
                .user(user1)
                .content(getMockMultifile(filename2,contentType,filepath2))
                .deadline(today)
                .height(20)
                .width(20)
                .build();

        tomorrowDtoReq=MessageDtoReq.builder()
                .user(user2)
                .content(getMockMultifile(filename3,contentType,filepath3))
                .deadline(tomorrow)
                .height(30)
                .width(30)
                .build();

        yesterdayMessage=messageService.dtoToEntity(yesterdayDtoReq);
        yesterdayMessageAfter=messageService.dtoToEntity(yesterdayDtoReq);
        yesterdayMessageAfter.setId(1l);
        yesterdayMessageAfter.setFilename(filename1);
        yesterdayMessageAfter.setFileUrl(filepath+"/"+filename1+"."+contentType);

        todayMessage=messageService.dtoToEntity(todayDtoReq);
        todayMessageAfter=messageService.dtoToEntity(todayDtoReq);
        todayMessageAfter.setId(2l);
        todayMessageAfter.setFilename(filename2);
        todayMessageAfter.setFileUrl(filepath+"/"+filename2+"."+contentType);

        tomorrowMessage=messageService.dtoToEntity(tomorrowDtoReq);
        tomorrowMessageAfter=messageService.dtoToEntity(tomorrowDtoReq);
        tomorrowMessageAfter.setId(2l);
        tomorrowMessageAfter.setFilename(filename3);
        tomorrowMessageAfter.setFileUrl(filepath+"/"+filename3+"."+contentType);

        yesterdayDtoRes=messageService.entityToDto(yesterdayMessageAfter);
        yesterdayDtoRes.setFilename(filename1);
        yesterdayDtoRes.setFileUrl(filepath1);

        todayDtoRes=messageService.entityToDto(todayMessageAfter);
        todayDtoRes.setFilename(filename2);
        todayDtoRes.setFileUrl(filepath2);

        tomorrowDtoRes=messageService.entityToDto(tomorrowMessageAfter);
        tomorrowDtoRes.setFilename(filename3);
        tomorrowDtoRes.setFileUrl(filepath3);
    }

    private void assertEqualDtoRes(MessageDtoRes dto1,MessageDtoRes dto2){
        assertThat(dto1.getDeadline()).isEqualTo(dto2.getDeadline());
        assertThat(dto1.getFilename()).isEqualTo(dto2.getFilename());
        assertThat(dto1.getFileUrl()).isEqualTo(dto2.getFileUrl());
        assertThat(dto1.getHeight()).isEqualTo(dto2.getHeight());
        assertThat(dto1.getWidth()).isEqualTo(dto2.getWidth());
    }

    @Nested
    @DisplayName("Success Case")
    class Success{
        @Test
        public void getByMessageId(){
            //given
            when(messageRepository.findById(anyLong())).thenReturn(Optional.ofNullable(todayMessageAfter));

            //when
            MessageDtoRes actual = messageService.getByMessageId(todayMessageAfter.getId());

            //then
            assertEqualDtoRes(actual,todayDtoRes);
        }

        @Test
        public void save() throws IOException {
            //given
            System.out.println(new Date());
            when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user1));
            when(messageRepository.save(any())).thenReturn(todayMessageAfter);
            doAnswer(new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) throws Throwable {
                    MessageDtoReq dto=(MessageDtoReq) invocation.getArgument(0);
                    return FileServiceDtoRes.builder()
                            .filename(dto.getFilename())
                            .fileUrl(dto.getFileUrl())
                            .build();
                }
            }).when(fileService).save(any());

            //when
            MessageDtoRes actual = messageService.save(user1.getId(), todayDtoReq);

            //then
            assertEqualDtoRes(actual,todayDtoRes);
        }
    }

    @Nested
    @DisplayName("Failed case")
    class Failed{
        @Test
        public void getByMessageIdFailed(){
            //given
            when(messageRepository.findById(1l)).thenThrow(MessageNotFoundException.class);
            //when & then
            assertThrows(MessageNotFoundException.class,()->messageService.getByMessageId(1l));
        }

        @Test
        public void saveFailed_사용자없음(){
            //given
            when(userRepository.findById(anyLong())).thenThrow(UserNotFoundException.class);
            //when & then
            assertThrows(UserNotFoundException.class,()->messageService.save(user1.getId(),todayDtoReq));
        }
    }
}
