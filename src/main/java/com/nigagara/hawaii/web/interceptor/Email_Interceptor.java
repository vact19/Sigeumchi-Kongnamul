package com.nigagara.hawaii.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.scaffold.MethodRegistry;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 쓸 필요 없음

/*@Slf4j
public class Email_Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getParameter("userName") == null){
            // @NotEmpty인 값이 비어있다면
            log.info(" ***preHandle Name NULL 검사- NULL 확인(GET) ***");
            return true;
        }

        *//**
         *  getParam Http Request-Name.을 읽어오고
         *  getAttribute.는 setAttr 한 값을 읽어온다.
         *//*
        String email1 = request.getParameter("email1");
        String email2 = request.getParameter("email2");


        StringBuffer sb = new StringBuffer();
        sb.append(email1);
        sb.append("@");
        sb.append(email2);
        log.info("**** 이메일 ****{}", sb.toString());
        request.setAttribute("email", sb.toString());
        return  true;
    }
}*/
