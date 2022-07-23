package TIAB.timebox.controller;

import TIAB.timebox.dto.UserDtoRes;
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

    @GetMapping
    public String home(HttpSession session, Model model){
        long id=(long) session.getAttribute("id");
        UserDtoRes user = userService.getUser(id);
        model.addAttribute("userDto",user);
        return "home";
    }
}
