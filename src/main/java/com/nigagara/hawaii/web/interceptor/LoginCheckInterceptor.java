package com.nigagara.hawaii.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {


    // 컨트롤러를 부르기 전 preHandle. 리턴값 true false 분기시켜서 로그인 관리
    // 화이트리스트 설정 등은 Config 설정클래스에서 맡아서 하므로 코드 간결함
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler
                      ) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("인증 체크 인터셉터 실행 {}", requestURI);

        HttpSession session = request.getSession(false); // false.로 해야 쓸데없는 세션생성이 없음

        // 서버에서 세션 속성을 등록하지 않았거나 제거당한(invalidate 로그아웃) 상태면 접근 불가
        if (session==null || session.getAttribute("userSession") == null){
            log.info("미인증 사용자 요청");
            // 홈으로 redirect
            // 쿼리 파라미터를 전해서 로그인 컨트롤러에서 처리. 다른 주소로 이동하면 쿼리 파라미터는 허공에 requestParam을 던지고, 로그인 주소로 이동하면 컨트롤러가 redirectURL이라는 파라미터를 받음
            //response.sendRedirect("/?redirectURL=" + requestURI);
            response.sendRedirect("/");
            return false;
        }

        return true;
    }
}