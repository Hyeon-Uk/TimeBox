package TIAB.timebox.service.member;

import TIAB.timebox.entity.member.Member;
import TIAB.timebox.service.security.OAuthUserInfo;

public interface MemberOAuthService {
    Member getOrRegistWithOAuthInfo(OAuthUserInfo userInfo);
}
