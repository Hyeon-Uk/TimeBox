package TIAB.timebox.controller;

import TIAB.timebox.dto.MemberDtoRes;
import TIAB.timebox.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
public class HomeController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public String home(@AuthenticationPrincipal OAuth2User oAuth2User, Model model){
        long id=oAuth2User.getAttribute("id");
        MemberDtoRes member = memberService.getMember(id);
        model.addAttribute("memberDto",member);
        return "home";
    }
}
