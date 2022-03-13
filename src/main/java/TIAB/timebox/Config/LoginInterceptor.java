package TIAB.timebox.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session=request.getSession();
        String requestUrl=request.getRequestURI();
        if(requestUrl.equals("/auth/login")){
            if(session.getAttribute("id")!=null){
                response.sendRedirect("/");
                return false;
            }
            else{
                return true;
            }
        }
        else {
            if (session.getAttribute("id") == null) {
                response.sendRedirect("/auth/login");
                return false;
            } else {
                return true;
            }
        }
    }
}
