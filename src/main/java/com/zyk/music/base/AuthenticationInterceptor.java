package com.zyk.music.base;


import com.zyk.music.utils.TokenUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 13540
 */
public class AuthenticationInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(@NotNull HttpServletRequest httpServletRequest, @NotNull HttpServletResponse httpServletResponse, @NotNull Object object) {
        String token = httpServletRequest.getHeader("token");
        String servletPath = httpServletRequest.getServletPath();
        if (servletPath.equals("/api/login")) {
            return true;
        }
        if(servletPath.equals("/api/addMusic")||servletPath.contains("playMusicFromYoutube")||servletPath.contains("favicon.ico")){
            return true;
        }
        TokenUtil.verify(token);
        return true;
    }

    @Override
    public void postHandle(@NotNull HttpServletRequest httpServletRequest,
                           @NotNull HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest httpServletRequest,
                                @NotNull HttpServletResponse httpServletResponse,
                                Object o, Exception e) {
    }
}