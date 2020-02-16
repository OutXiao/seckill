package com.wenfan.seckill.filter;

import com.wenfan.seckill.service.TokenService;
import com.wenfan.seckill.utils.ClientUtils;
import com.wenfan.seckill.vo.LoginUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wenfan on 2019/12/29 11:14
 */
@Component
public class TokenFilter extends OncePerRequestFilter {

    private static final String TOKEN_KEY = "token";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final long Minutes_10 = 10 * 60 * 1000;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //设置response，否则前端页面拿不到option结果
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");  //允许跨域请求
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "token");
        String method = httpServletRequest.getMethod();
        if (method.equals("OPTIONS")) {
            //预请求需要往回写 让ajax预请求知道预请求是成功的
            httpServletResponse.setStatus(202);
            httpServletResponse.getWriter().write("opiotns请求成功");
        } else {
            System.out.println(httpServletRequest.getRequestURI());

            String token = getToken(httpServletRequest);
            if (StringUtils.isNotBlank(token)) {
                LoginUser loginUser = tokenService.getLoginUser(token);
                if (loginUser != null) {
                    loginUser = checkLoignTime(loginUser);
                    // 获取认证
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }


    }


    private LoginUser checkLoignTime(LoginUser loginUser) {
        long lastLoginTime = loginUser.getLoginTime();
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastLoginTime) < Minutes_10) {
            String token = loginUser.getToken();
            loginUser = (LoginUser) userDetailsService.loadUserByUsername(loginUser.getUsername());
            loginUser.setToken(token);
            tokenService.freshToken(loginUser);
        }
        return loginUser;
    }


    // 从请求中获取 token
    public static String getToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getParameter(TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            token = httpServletRequest.getHeader(TOKEN_KEY);
        }
        return token;
    }
}
