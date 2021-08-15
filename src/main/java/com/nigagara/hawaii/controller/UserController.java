package com.nigagara.hawaii.controller;

import com.nigagara.hawaii.entity.User;
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

        model.addAttribute("userForm", new UserFormDTO());
        //model로 빈 객체 넘겨서 표시.  주소창 단순입력으로 접근 불가
        return "user/createUserForm";
    }

    @PostMapping("/user/new")
    public String createUserPost(@Valid UserFormDTO form, BindingResult bindingResult,
                                 HttpServletRequest request, RedirectAttributes rttr)
    {
        if(bindingResult.hasErrors()){
            return "user/createUserForm";
        }
        User user = setUserField(form, new User());
        userService.joinUser(user);

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(5);
        session.setAttribute("userSession",form.getUserName());

        return "redirect:/hello";
    }
    @GetMapping("/hello")
    public String hello(Model model, HttpServletRequest request, HttpSession httpSession){

        System.out.println(httpSession.getId());
        System.out.println(request.getSession().getId());

        if (httpSession.getId().equals(request.getSession().getId())){
            model.addAttribute("session",httpSession);
        }
        return "home";
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


















