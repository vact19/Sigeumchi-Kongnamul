package com.nigagara.hawaii.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @RequestMapping("/") // 모든 방식에 반응
    public String home(){
        return "home";
    }
}
