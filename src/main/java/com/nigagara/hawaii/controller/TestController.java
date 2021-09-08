package com.nigagara.hawaii.controller;

import com.nigagara.hawaii.entity.CommentData;
import com.nigagara.hawaii.entity.TestComment;
import com.nigagara.hawaii.entity.TestEntity;
import com.nigagara.hawaii.service.CommentService;
import com.nigagara.hawaii.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
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

    /**
             댓글 식별자는 개발단계 확인용.
     */
    @PostMapping("/test/{id}/addComment")
    @Transactional // 코드가 짧아 Service를 거치지 않기로 함
    public String addComment(@PathVariable Long id,
                             @RequestParam String textBox, @RequestParam String userName
                             ){
        //id를 이용한 crud. 그중 C
        // showTestView에서 모델에 실려가는 sortedTest에 반영되게끔
        // TestEntity id를 찾아서 다대일 매핑을 이용해 TestComment를 persist하고 끝낸다

        CommentData data = new CommentData(userName, textBox, 0L);
        TestComment comment = new TestComment();
        comment.setCommentData(data);
        comment.setTestEntity(em.find(TestEntity.class, id));
        em.persist(comment);

        return "redirect:/test/"+id;
    }

    @PostMapping("/test/{id}/updateComment")
    @Transactional
    public String updateComment(@PathVariable Long id, @RequestParam("textBoxU") String content,
                                @RequestParam("commId") Long commentId, HttpServletRequest request
                                ,Model model, RedirectAttributes rttr)
    {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userSession");
        /** 해당 번호 글의 댓글들을 리스트로 모두 불러와
         *   행 번호로 특정 댓글을 찾아내는 방식
         *   -> 댓글 식별자를 이용해 하나의 댓글만 찾아와 수정하도록 변경.
         */
        // 2줄로 끝
        TestComment comment = em.find(TestComment.class, commentId);
        if( (comment.getCommentData().getUserName()).equals(userName) ){
            comment.getCommentData().setContent(content);
            return "redirect:/test/{id}";
        }
        rttr.addFlashAttribute("authError", "권한 없음");
        log.info(" UPDATE 권한없음 전송");
        return "redirect:/test/{id}";
    }

    @PostMapping("/test/{id}/deleteComment")
    @Transactional
    public String deleteComment(@PathVariable Long id, @RequestParam("commentId") Long commentId
                                    , RedirectAttributes rttr, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String  username = (String) session.getAttribute("userSession");

        TestComment comment = em.find(TestComment.class, commentId);
        if( (comment.getCommentData().getUserName()).equals(username) ){
            em.remove(comment);
            return "redirect:/test/{id}";
        }

        rttr.addFlashAttribute("authError", "권한 없음");
        log.info(" UPDATE 권한없음 전송");
        return "redirect:/test/{id}";
    }














}




















