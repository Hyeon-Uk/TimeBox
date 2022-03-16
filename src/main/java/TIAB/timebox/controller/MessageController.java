package TIAB.timebox.controller;

import TIAB.timebox.domain.Message;
import TIAB.timebox.dto.MessageDtoTemp;
import TIAB.timebox.service.MessageService;
import TIAB.timebox.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    public static byte[] decodeBase64ToBytes(String imageString) {
        return Base64.getDecoder().decode(imageString);
    }

    @PostMapping("/")
    public String sendMessage(HttpSession session, MessageDto message) throws IOException {
        long userId=(Long)session.getAttribute("id");
        if(message.getDeadline()==null){
            return "redirect:/message";
        }
        messageService.save(userId,message);
        return "redirect:/";
    }
}
