package TIAB.timebox.controller;

import TIAB.timebox.dto.UserDtoRes;
import TIAB.timebox.exception.UserNotFoundException;
import TIAB.timebox.service.message.MessageService;
import TIAB.timebox.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@Slf4j
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @GetMapping
    public String home(HttpSession session, Model model) throws Exception {
        long id=(long) session.getAttribute("id");
        UserDtoRes user = userService.getUser(id);
        model.addAttribute("userDto",user);
        return "home";
    }

    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFoundException(UserNotFoundException e,RedirectAttributes redirectAttributes){
        log.error("user not founded");
        redirectAttributes.addAttribute("error",e.getMessage());
        return "redirect:/auth/login";
    }
}
