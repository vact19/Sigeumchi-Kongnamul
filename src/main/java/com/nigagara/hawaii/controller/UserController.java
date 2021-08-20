package com.nigagara.hawaii.controller;

import com.nigagara.hawaii.entity.User;
import com.nigagara.hawaii.service.LoginResult;
import com.nigagara.hawaii.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/new")
    public String createUser(Model model){

        model.addAttribute("userFormDTO", new UserFormDTO());
        //model로 빈 객체 넘겨서 표시.  주소창 단순입력으로 접근 불가
        return "user/createUserForm";
    }


    @PostMapping("/user/new")
    public String createUserPost(@Valid UserFormDTO form, BindingResult bindingResult,
                                 HttpServletRequest request, Model model)
    {
        if(bindingResult.hasErrors()){
            /**
               Form @ModelAttribute 생략. 기본 key 값은 camel case "userFormDTO"
             반드시 빈 객체의 key 값과 Error Return.의  key.값 일치해야 함
             ( "userFormDTO" )
             */
            return "user/createUserForm"; // 파일명이 아니라 주소명으로 보내면 양식 초기화될듯
        }

        User user = setUserField(form, new User());
        userService.joinUser(user);

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(10); // 세션 n초.
        session.setAttribute("userSession",form.getUserName());

        System.out.println(session.getId());
        System.out.println(request.getSession().getId());

        if (session.getId().equals(request.getSession().getId())){
            model.addAttribute("session",session);
        }

        return "redirect:/";
        //return "redirect:/hello";
    }
    //
//    @GetMapping("/hello")
//    public String hello(Model model, HttpServletRequest request, HttpSession httpSession){
//
//        System.out.println(httpSession.getId());
//        System.out.println(request.getSession().getId());
//
//        if (httpSession.getId().equals(request.getSession().getId())){
//            model.addAttribute("session", httpSession);
//        }
//
//
//        return "home";
//    }

    @PostMapping("/user/login")
    public String login( LoginFormDTO form, HttpServletRequest request
                        , Model model)
    /**
     *  LoginFormDTO 모델에 자동 전송 안되는 문제가 생겨
     *  조건문 분기점마다 add()
     */
    {
        LoginResult result = userService.login(form.getUserName(), form.getPassword());
        System.out.println("result = " + result);
        if ( result == LoginResult.NO_SUCH_ID ){
            //ID.부터 틀렸으니 양식을 비워서 다시 전달
            form.setUserName("");  form.setPassword("");
            model.addAttribute("idError", "그런 id 없어요");
            model.addAttribute("loginFormDTO",form);
            return "home";
        } else if ( result == LoginResult.ONLY_ID ){
            form.setPassword(""); // Password만 비워주고 돌려보냄
            model.addAttribute("pwdError", "PWD 다시 입력");
            model.addAttribute("loginFormDTO",form);
            return "home";
        } else if (result == LoginResult.SUCCESS) {

            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(10); // 세션 ㅜ초임.
            session.setAttribute("userSession", form.getUserName());
            if (session.getId().equals(request.getSession().getId())) {
                model.addAttribute("session", session);
            }
            return "redirect:/";
        }
        return "unexpectedError";
    }
    private User setUserField(UserFormDTO form, User user) {
        user.setUserName(form.getUserName());
        user.setEmail(form.getEmail()+"@"+form.getEmail2());
        user.setPassword(form.getPassword());
        user.setPwdQuestion(form.getPwdQuestion());
        user.setPwdAnswer(form.getPwdAnswer());
        user.setPwdHint(form.getPwdHint());
        user.setRegDate(LocalDateTime.now());
        return user;
    }

}


















