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

import javax.persistence.EntityManager;
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
    public String updateComment(@PathVariable Long id,  @RequestParam("textBoxU") String content,
                                @RequestParam("commRow") Long index){

        log.info(" update 컨트롤러 도착 ");

        // 해당 TestEntity에 맞는 댓글들 불러오기
        TestEntity testEntity = em.find(TestEntity.class, id);
        List<TestComment> comments = commentService.findComments(testEntity);

        // 댓글 수정하기
        TestComment comment = comments.get(index.intValue());
        CommentData existingData = comment.getCommentData();
        CommentData newData = new CommentData(existingData.getUserName(), content, existingData.getLikes());
        comment.setCommentData(newData);
        comments.set(index.intValue(), comment);

        return "redirect:/test/{id}";
    }

    @PostMapping("/test/{id}/deleteComment")
    @Transactional
    public String deleteComment(@PathVariable Long id, @RequestParam("index") int rowIndex)
    {
        TestEntity testEntity = em.find(TestEntity.class, id);
        List<TestComment> comments = commentService.findComments(testEntity);

        TestComment comment = comments.get(rowIndex);
        em.remove(comment);
        return "redirect:/test/{id}";
    }














}




















