package TIAB.timebox.controller;

import TIAB.timebox.dto.MemberDTO;
import TIAB.timebox.service.member.MemberService;
import TIAB.timebox.service.security.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;

    @GetMapping
    public String home(@AuthenticationPrincipal CustomOAuth2User oAuth2User, Model model) {
        MemberDTO.Response member = memberService.findById(MemberDTO.Request.builder()
                .id(oAuth2User.getId())
                .build());
        model.addAttribute("member", member);
        return "home";
    }
}
