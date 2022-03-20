package TIAB.timebox.controller;

import TIAB.timebox.entity.User;
import TIAB.timebox.security.KakaoOAuth;
import TIAB.timebox.security.KakaoProfile;
import TIAB.timebox.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Value("${kakao.key}")
    private String appKey;
    @Value("${kakao.redirect}")
    private String redirect;

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("key",appKey);
        model.addAttribute("redirect",redirect);
        return "login";
    }
    @GetMapping("/logout")
    public String logoutPage(HttpSession session){
        session.removeAttribute("email");
        session.removeAttribute("kakao_id");
        session.removeAttribute("id");
        return "redirect:/auth/login";
    }
    @GetMapping("/kakao/callback")
    public String kakaologin(HttpSession session, @RequestParam("code")String code){
        User user=userService.socialLogin(code);
        User savedUser=userService.save(user);

        session.setAttribute("email",savedUser.getEmail());
        session.setAttribute("kakao_id",savedUser.getKakaoId());
        session.setAttribute("id",savedUser.getId());
        return "redirect:/";
    }
}
