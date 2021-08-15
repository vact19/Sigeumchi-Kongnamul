package com.nigagara.hawaii.controller;

import com.nigagara.hawaii.entity.User;
import com.nigagara.hawaii.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/") // 모든 방식에 반응
    public String home(){
        return "home";
    }

    @GetMapping("/{id}")
    public String clickTest(@PathVariable String id){ // String id
        return "home";
    }

    @GetMapping("/users")
    public String generateUserList(Model model){

        List<User> users = userService.findUsers();
        model.addAttribute("users",users);
        return "/user/userList"; // userList.html
    }




}

















