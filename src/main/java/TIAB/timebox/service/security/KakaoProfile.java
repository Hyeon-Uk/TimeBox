package TIAB.timebox.service.security;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KakaoProfile {
    private long id;
    private String connected_at;
    private Properties properties;
    private KakaoAccount kakao_account;

    @Getter
    @ToString
    public static class Properties {
        private String nickname;
        private String profile_image;
        private String thumbnail_image;
    }

    @Getter
    @ToString
    public static class KakaoAccount {
        private Boolean profile_nickname_needs_agreement;
        private Boolean profile_image_needs_agreement;
        private Profile profile;
        private Boolean has_email;
        private Boolean email_needs_agreement;
        private Boolean is_email_valid;
        private Boolean is_email_verified;
        private String email;
        private Boolean has_age_range;
        private Boolean age_range_needs_agreement;
        private Boolean has_birthday;
        private Boolean birthday_needs_agreement;
        private Boolean has_gender;
        private Boolean gender_needs_agreement;

        @Getter
        @ToString
        public static class Profile {
            private String nickname;
            private String thumbnail_image_url;
            private String profile_image_url;
            private Boolean is_default_image;
        }
    }
}
