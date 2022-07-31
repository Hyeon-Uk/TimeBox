package TIAB.timebox.controller;

import TIAB.timebox.dto.UserDtoRes;
import TIAB.timebox.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
@Slf4j
public class HomeController {

    @Autowired
    private UserService userService;

//    @GetMapping
//    public String home(HttpSession session, Model model){
//        long id=(long) session.getAttribute("id");
//        UserDtoRes user = userService.getUser(id);
//        model.addAttribute("userDto",user);
//        return "home";
//    }
    @GetMapping
    public String home(@AuthenticationPrincipal OAuth2User oAuth2User, Model model){
        long id=oAuth2User.getAttribute("id");
        UserDtoRes user = userService.getUser(id);
        model.addAttribute("userDto",user);
        return "home";
    }
}
