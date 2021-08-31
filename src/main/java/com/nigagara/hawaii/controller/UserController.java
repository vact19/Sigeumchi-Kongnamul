package com.nigagara.hawaii.controller;

import com.nigagara.hawaii.controller.DTO.LoginFormDTO;
import com.nigagara.hawaii.controller.DTO.TypeMismatchTest;
import com.nigagara.hawaii.controller.DTO.UserFormDTO;
import com.nigagara.hawaii.entity.User;
import com.nigagara.hawaii.service.LoginResult;
import com.nigagara.hawaii.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired SmartValidator validator;
    private final UserService userService;

    @GetMapping("/user/new")
    public String createUser(Model model){
        model.addAttribute("userFormDTO", new UserFormDTO());
        model.addAttribute("typeMismatchTest", new TypeMismatchTest());
        //model로 빈 객체 넘겨서 표시.  주소창 단순입력으로 접근 불가
        return "user/createUserForm";
    }
    @PostMapping("/user/new/typetest")
    public String validatePrice(@Valid TypeMismatchTest typeTest, BindingResult bindingResult,
                                UserFormDTO form){ // 에러떠서 돌아갈때 userForm.도 모델에 담아가야 함

        // Optional 사용해보기
        int total = Optional.ofNullable(typeTest.getPrice1()).orElse(0)
                + Optional.ofNullable(typeTest.getPrice2()).orElse(0);
        if ( total < 10000){
            // GlobalError를 추가하는 reject()
            bindingResult.reject("totalPriceMin", new Object[]{10000, total}, null);
        }

        if (bindingResult.hasErrors()){
            log.info("ERROR? = {}", bindingResult);
            return "user/createUserForm";
        }
        return "redirect:/";
    }

    @PostMapping("/user/new")
    public String createUserPost(@Valid UserFormDTO form, BindingResult bindingResult,
                                 HttpServletRequest request, TypeMismatchTest test, Model model)
    {                                    // 에러떠서 돌아갈때 TypeMismatchTest.도 모델에 담아가야 함
        if(bindingResult.hasErrors()){
            /**
               Form @ModelAttribute 생략. 기본 key 값은 camel case "userFormDTO"
             반드시 빈 객체의 key 값과 Error Return.의  key.값 일치해야 함
             ( "userFormDTO" )
             */
            return "user/createUserForm"; // 파일명이 아니라 주소명으로 보내면 양식 초기화될듯
        }
        /** Email Interceptor preHandle.에서 등록한 email
         */
        form.setEmail((String) request.getAttribute("email"));
        validator.validate(form, bindingResult);
        if(bindingResult.hasErrors()){
            return "user/createUserForm";
        }
        User user = setUserField(form, new User());
        userService.joinUser(user);

        return "redirect:/";
    }

    @PostMapping("/user/login")
    public String login(LoginFormDTO form, HttpServletRequest request
                        , Model model, RedirectAttributes redirectAttributes)
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

            HttpSession session = request.getSession(true); // 세션 생성
            session.setMaxInactiveInterval(30); // 세션 ㅜ초임.
            session.setAttribute("userSession", form.getUserName());

            /** session 객체 모델 전송은 X. 타임리프에서 자동 처리
             */
           // redirect 되는 뷰에 모델로 보내지는
            // 듯 함.
           redirectAttributes.addFlashAttribute("session11", "rttr.addFlashAttribute()");
            return "redirect:/";
        }
        return "unexpectedError";
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/";
    }



    private User setUserField(UserFormDTO form, User user) {
        user.setUserName(form.getUserName());
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setPwdQuestion(form.getPwdQuestion());
        user.setPwdAnswer(form.getPwdAnswer());
        user.setPwdHint(form.getPwdHint());
        user.setRegDate(LocalDateTime.now());
        return user;
    }

}


















