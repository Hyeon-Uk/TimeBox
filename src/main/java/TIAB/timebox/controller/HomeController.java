package TIAB.timebox.controller;

import TIAB.timebox.dto.MemberDtoRes;
import TIAB.timebox.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;

    @GetMapping
    public String home(@AuthenticationPrincipal OAuth2User oAuth2User, Model model) {
        long id = oAuth2User.getAttribute("id");
        MemberDtoRes member = memberService.getMember(id);
        model.addAttribute("memberDto", member);
        return "home";
    }
}
