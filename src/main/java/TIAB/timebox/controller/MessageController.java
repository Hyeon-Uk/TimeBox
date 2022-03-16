package TIAB.timebox.controller;

import TIAB.timebox.domain.Message;
import TIAB.timebox.service.MessageService;
import TIAB.timebox.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/")
    public String sendMessage(HttpSession session, MessageDto message){
        log.info("enter");
        long userId=(Long)session.getAttribute("id");
        messageService.save(userId,message);
        return "redirect:/";
    }
}
