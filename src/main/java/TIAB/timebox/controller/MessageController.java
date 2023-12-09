package TIAB.timebox.controller;

import TIAB.timebox.dto.MessageDtoReq;
import TIAB.timebox.dto.MessageDtoRes;
import TIAB.timebox.exception.CanNotAccessException;
import TIAB.timebox.exception.NotPassedDeadlineException;
import TIAB.timebox.service.message.MessageService;
import TIAB.timebox.service.security.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public String makeMessage() {
        return "make";
    }

    @PostMapping
    public String sendMessage(@AuthenticationPrincipal CustomOAuth2User oAuth2User,
                              MessageDtoReq messageDtoReq) throws IOException {
        long memberId = oAuth2User.getId();
        if (messageDtoReq.getDeadline() == null) {
            return "redirect:/message";
        }
        messageService.save(memberId, messageDtoReq);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showMessage(@PathVariable("id") String id,
                              @AuthenticationPrincipal CustomOAuth2User oAuth2User, Model model) {
        Date now = new Date();
        long memberId = oAuth2User.getId();
        MessageDtoRes messageDtoRes = messageService.getByMessageId(Long.parseLong(id));

        if (now.getTime() < messageDtoRes.getDeadline().getTime()) {
            throw new NotPassedDeadlineException();
        }
        if (messageDtoRes.getMember().getId() != memberId) {
            throw new CanNotAccessException();
        }

        model.addAttribute("messageDto", messageDtoRes);
        return "message";
    }
}
