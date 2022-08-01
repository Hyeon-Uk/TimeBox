package TIAB.timebox.controller;

import TIAB.timebox.exception.CanNotAccessException;
import TIAB.timebox.exception.MessageNotFoundException;
import TIAB.timebox.exception.NotPassedDeadlineException;
import TIAB.timebox.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler
    public String userNotFoundException(UserNotFoundException e, RedirectAttributes redirectAttributes){
        log.error("user not founded");
        redirectAttributes.addAttribute("error",e.getMessage());
        return "redirect:/auth/login";
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public String messageNotFoundException(MessageNotFoundException e,RedirectAttributes redirectAttributes){
        log.error("message not found");
        redirectAttributes.addAttribute("error",e.getMessage());
        return "redirect:/";
    }


    @ExceptionHandler
    public String notPassedDeadlineException(NotPassedDeadlineException e,RedirectAttributes redirectAttributes){
        log.error("not passed deadline");
        redirectAttributes.addAttribute("error",e.getMessage());
        return "redirect:/";
    }

    @ExceptionHandler
    public String canNotAccessException(CanNotAccessException e,RedirectAttributes redirectAttributes){
        log.error("can not access this message");
        redirectAttributes.addAttribute("error",e.getMessage());
        return "redirect:/";
    }
}
