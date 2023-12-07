package TIAB.timebox.service.file;

import TIAB.timebox.dto.FileServiceDtoRes;
import TIAB.timebox.dto.MessageDtoReq;

import java.io.IOException;
import java.util.Date;

public interface FileService {
    public FileServiceDtoRes save(MessageDtoReq messageDtoReq) throws IOException;

    default String generateFilename(MessageDtoReq dto, String contentType) {
        Date date = new Date();
        return Long.toString(date.getTime()) + contentType;
    }
}
