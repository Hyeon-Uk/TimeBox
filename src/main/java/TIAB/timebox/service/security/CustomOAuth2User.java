package TIAB.timebox.service.security;

import TIAB.timebox.entity.member.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@Getter
public class CustomOAuth2User implements OAuth2User {
    private final Member member;
    private final Long id;
    private final String socialId;
    private final String socialProvider;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(Member member, Map<String, Object> attributes) {
        this.attributes = attributes;
        this.member = member;
        this.id = member.getId();
        this.socialProvider = member.getSocialProvider();
        this.socialId = member.getSocialId();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority("MEMBER"));
    }

    @Override
    public String getName() {
        return this.member.getEmail();
    }
}
