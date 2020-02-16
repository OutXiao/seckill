package com.wenfan.seckill.filter;

import com.wenfan.seckill.properties.SecurityProperties;
import com.wenfan.seckill.utils.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wenfan on 2020/1/26 21:11
 */
//@Component("authorzationFilter")
public class AuthorizationFilter extends OncePerRequestFilter implements InitializingBean {


    @Autowired
    private SecurityProperties securityProperties;

    private Set<String> unAuthorizationSet = new HashSet<>();

    private AntPathMatcher antPathMatcher = new AntPathMatcher();


    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        addUrlToSet(securityProperties.getBrowser().getUnAuthorizationUrl());
    }

    private void addUrlToSet(String unAuthorizationUrl) {

        if (StringUtils.isAnEmpty(unAuthorizationUrl)){
            String [] urls = unAuthorizationUrl.split(",");
            for(String url:urls){
                unAuthorizationSet.add(url);
                System.out.println(url);
            }
        }

    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        for (String url:unAuthorizationSet)
        if (antPathMatcher.match(url,httpServletRequest.getRequestURI())){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }
}
