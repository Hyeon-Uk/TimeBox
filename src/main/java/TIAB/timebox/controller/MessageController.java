package TIAB.timebox.controller;

import TIAB.timebox.dto.MessageDtoReq;
import TIAB.timebox.dto.MessageDtoRes;
import TIAB.timebox.exception.MessageNotFoundException;
import TIAB.timebox.exception.NotPassedDeadlineException;
import TIAB.timebox.service.message.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String showMessage(@PathVariable("id") String id,Model model, RedirectAttributes redirectAttributes) throws Exception {
        Date now=new Date();
        MessageDtoRes messageDtoRes=messageService.getByMessageId(Long.parseLong(id));
        if(now.getTime()<messageDtoRes.getDeadline().getTime()) throw new NotPassedDeadlineException();
        model.addAttribute("messageDto",messageDtoRes);
        return "message";
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public String messageNotFoundException(MessageNotFoundException e,RedirectAttributes redirectAttributes){
        log.error("message not found");
        redirectAttributes.addAttribute("error",e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler(NotPassedDeadlineException.class)
    public String notPassedDeadlineException(NotPassedDeadlineException e,RedirectAttributes redirectAttributes){
        log.error("not passed deadline");
        redirectAttributes.addAttribute("error",e.getMessage());
        return "redirect:/";
    }
}
