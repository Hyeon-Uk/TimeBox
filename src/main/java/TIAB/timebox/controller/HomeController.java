package TIAB.timebox.controller;
import TIAB.timebox.entity.user.User;
import TIAB.timebox.service.message.MessageService;
import TIAB.timebox.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;


@Controller
@Slf4j
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @GetMapping
    public String home(HttpSession session, Model model){
        String email= (String) session.getAttribute("email");
        long id=(long) session.getAttribute("id");
        if(email!=null){
            User user= userService.getUser(id);
            model.addAttribute("messages",user.getMessages());
            model.addAttribute("user",user);
        }
        return "home";
    }
}
