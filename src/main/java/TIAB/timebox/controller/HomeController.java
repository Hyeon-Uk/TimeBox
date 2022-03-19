package TIAB.timebox.controller;
import TIAB.timebox.domain.Message;
import TIAB.timebox.domain.User;
import TIAB.timebox.repository.MessageDao;
import TIAB.timebox.security.KakaoOAuth;
import TIAB.timebox.security.KakaoProfile;
import TIAB.timebox.service.MessageService;
import TIAB.timebox.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
@Slf4j
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/")
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

    @GetMapping("/message")
    public String makeMessage(Model model){
        return "make";
    }

}
