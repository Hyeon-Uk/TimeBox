package TIAB.timebox.controller;
import TIAB.timebox.domain.User;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@Slf4j
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private KakaoOAuth kakaoOAuth;

    @Value("${kakao.key}")
    private String appKey;
    @Value("${kakao.redirect}")
    private String redirect;

    @RequestMapping(value="/")
    public String home(HttpSession session, Model model){
        String email= (String) session.getAttribute("email");
        if(email!=null){
            model.addAttribute("email",email);
        }
        return "home";
    }
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
        return "redirect:/login";
    }
    @GetMapping("/auth/kakao/callback")
    public String kakaologin(HttpSession session,RedirectAttributes redirectAttributes, @RequestParam("code")String code){
        KakaoProfile profile=kakaoOAuth.getUserInfo(code);
        User user=new User();
        user.setKakaoId(profile.getId());
        user.setEmail(profile.getKakao_account().getEmail());

        User savedUser=userService.save(user);
        session.setAttribute("email",savedUser.getEmail());
        session.setAttribute("kakao_id",savedUser.getKakaoId());
        session.setAttribute("id",savedUser.getId());
        return "redirect:/";
    }
}
