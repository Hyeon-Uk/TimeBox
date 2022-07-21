package TIAB.timebox.controller;

import TIAB.timebox.dto.MessageDtoRes;
import TIAB.timebox.entity.message.Message;
import TIAB.timebox.service.message.MessageService;
import TIAB.timebox.dto.MessageDtoReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
@Slf4j
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping
    public String makeMessage(Model model){
        return "make";
    }

    @PostMapping
    public String sendMessage(HttpSession session, MessageDtoReq messageDtoReq) throws IOException {
        long userId=(Long)session.getAttribute("id");
        if(messageDtoReq.getDeadline()==null){
            return "redirect:/message";
        }
        messageService.save(userId, messageDtoReq);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showMessage(HttpSession session, @PathVariable("id") String id,Model model, RedirectAttributes redirectAttributes){
        Date now=new Date();
        MessageDtoRes messageDtoRes=messageService.getByMessageId(Long.parseLong(id));
        if(messageDtoRes!=null){
            if(now.getTime()<messageDtoRes.getDeadline().getTime()){
                redirectAttributes.addAttribute("error","시간이 아직 안지났습니다.");
                return "redirect:/";
            }
            model.addAttribute("messageDto",messageDtoRes);
            return "message";
        }
        else return "redirect:/";
    }
}
