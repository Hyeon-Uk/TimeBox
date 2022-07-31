package TIAB.timebox.controller;

import TIAB.timebox.repository.MessageRepository;
import TIAB.timebox.repository.UserRepository;
import TIAB.timebox.service.message.MessageService;
import TIAB.timebox.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithAnonymousUser
    public void canNotAccess() throws Exception {
        mvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void canAccess() throws Exception {
        mvc.perform(get("/").with(oauth2Login()
                        .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                        .attributes(attr->{
                            attr.put("id",1l);
                        })
                ))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
