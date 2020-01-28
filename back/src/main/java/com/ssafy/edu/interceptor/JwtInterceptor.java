package com.ssafy.edu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.edu.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    @Autowired
    private JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler)
            throws Exception {
    	log.info("===============================");
        log.info(request.getMethod() + " :" + request.getServletPath());
        log.info("===============================");
        // option 요청은 바로 통과시켜주자.
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        } else {
        	String logintoken = request.getHeader("login-token");

            if(logintoken != null && logintoken.length() > 0) {            	
            	jwtService.checkValid(logintoken);
            	log.trace("토큰 사용 가능: {}", logintoken);
            	return true;
            }
            else {
            	throw new RuntimeException("인증 토큰이 없습니다.");            	
            }
        }
    }
}