package TIAB.timebox.service.file;

import TIAB.timebox.dto.FileServiceDtoRes;
import TIAB.timebox.dto.MessageDtoReq;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public FileServiceDtoRes save(MessageDtoReq messageDtoReq) throws IOException {
        MultipartFile file= messageDtoReq.getContent();
        String absolutePath=System.getProperty("user.dir")+"/src/main/resources/static/messagebox";
        File dir=new File(absolutePath);
        if(!dir.exists()) dir.mkdir();

        String filename=generateFilename(messageDtoReq,".png");
        String fileUrl="/messagebox/"+filename;

        File saveFile=new File(absolutePath,filename);
        file.transferTo(saveFile);

        return FileServiceDtoRes.builder()
                .filename(filename)
                .fileUrl(fileUrl)
                .build();
    }
}
