package TIAB.timebox.service.member;

import TIAB.timebox.entity.member.Member;
import TIAB.timebox.repository.MemberRepository;
import TIAB.timebox.service.security.OAuthUserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberOAuthServiceImpl implements MemberOAuthService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Member getOrRegistWithOAuthInfo(OAuthUserInfo userInfo) {
        String email = userInfo.getEmail();
        String socialId = userInfo.getSocialId();
        String socialProvider = userInfo.getSocialProvider();
        String profileImageUrl = userInfo.getProfileImageUrl();

        Member member = memberRepository.findByEmail(email)
                .orElse(Member.builder()
                        .socialProvider(socialProvider)
                        .socialId(socialId)
                        .email(email)
                        .build());
        member.updateProfileImg(profileImageUrl);
        return memberRepository.save(member);
    }
}
