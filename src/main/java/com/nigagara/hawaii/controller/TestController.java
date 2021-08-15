package com.nigagara.hawaii.controller;

import com.nigagara.hawaii.entity.TestComment;
import com.nigagara.hawaii.entity.TestEntity;
import com.nigagara.hawaii.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final EntityManager em;
    private final CommentService commentService;

    @GetMapping("/test/{id}")
    public String showTestView(@PathVariable Long id, Model model){
        // 테스트 번호 PathVar

        TestEntity testEntity = em.find(TestEntity.class, id);

        List<TestComment> comments = commentService.findComments(testEntity);

        model.addAttribute("comments", comments);
        model.addAttribute("testId", id);
        model.addAttribute("testName", testEntity.getTestName());

        return "/test/goTest";
    }

}
