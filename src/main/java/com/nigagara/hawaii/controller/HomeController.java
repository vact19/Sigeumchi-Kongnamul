package com.nigagara.hawaii.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/") // 모든 방식에 반응
    public String home(Model model,
                       HttpServletRequest request, HttpSession httpSession){
        if (httpSession.getId().equals(request.getSession().getId())){
            model.addAttribute("mySession","session");
        }

        return "home";
    }

    @GetMapping("/{id}")
    public String clickTest(@PathVariable int id){
        return "home";
    }


}
