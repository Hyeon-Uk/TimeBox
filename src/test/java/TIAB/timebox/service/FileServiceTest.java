package TIAB.timebox.service;

import TIAB.timebox.dto.FileServiceDtoRes;
import TIAB.timebox.dto.MessageDtoReq;
import TIAB.timebox.service.file.FileServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {
    @Mock
    private FileServiceImpl fileService;


    @Test
    @DisplayName("파일 저장 테스트")
    public void success() throws IOException {
        MessageDtoReq messageDtoReq = MessageDtoReq.builder()
                .content(null)
                .deadline(new Date())
                .height(15)
                .width(15)
                .build();
        FileServiceDtoRes res = FileServiceDtoRes.builder()
                .filename("test")
                .fileUrl("testurl")
                .build();
        when(fileService.save(any())).thenReturn(res);

        assertThat(fileService.save(messageDtoReq)).isEqualTo(res);
    }
}
