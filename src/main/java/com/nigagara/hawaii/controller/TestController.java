package com.nigagara.hawaii.controller;

import com.nigagara.hawaii.entity.TestComment;
import com.nigagara.hawaii.entity.TestEntity;
import com.nigagara.hawaii.service.CommentService;
import com.nigagara.hawaii.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TestController {
    /**
     * TestController 에서 Comment, Test.에 대한 Repository, Service 모두 사용
     */
    private final EntityManager em;
    private final CommentService commentService;
    private final TestService testService;

    //
    @Transactional // 데이터 변경
    @GetMapping("/test/{id}")
    public String showTestView(@PathVariable Long id, Model model){

        // 테스트 번호 PathVar
        TestEntity testEntity = em.find(TestEntity.class, id);
        testEntity.setCount(testEntity.getCount()+1); // @PathVar 조회수 +1 Dirty Checking
        // 데이터가 없으면 null

        List<TestComment> comments = commentService.findComments(testEntity);

        model.addAttribute("comments", comments);
        model.addAttribute("testId", id);
        model.addAttribute("testName", testEntity.getTestName());

        // 조회수 순위
        List<TestEntity> sortedTest = testService.sortTests();
        model.addAttribute("sorted", sortedTest);
        return "/test/goTest";
    }














}




















