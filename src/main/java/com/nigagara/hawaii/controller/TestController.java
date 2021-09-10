package com.nigagara.hawaii.controller;

import com.google.gson.JsonObject;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
     // 아래 CRUD 는 코드가 짧아 Service를 거치지 않기로 함
     */
    @PostMapping("/test/{id}/addComment")
    @Transactional
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
        rttr.addFlashAttribute("authError", "수정 권한 없음");
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
            log.info("여기 진입");
            // 댓글 삭제 전에 댓글 대상의 좋아요를 모두 빼낸다.
            // 좋아요의 PK로 조회하는 것이 아니므로 delete JPQL
            Query deleteQuery = em.createQuery("delete from Likes  L where  " +
                    "L.testComment =:deleteComment")
                    .setParameter("deleteComment", comment);
            deleteQuery.executeUpdate(); //Execute an update or delete statement.
            // 수정 삭제는 따로 executeUpdate() 실행해야 함
            // Query -- Interface used to control query execution.
            em.remove(comment);
            return "redirect:/test/{id}";
        }

        rttr.addFlashAttribute("authError", "삭제 권한 없음");
        log.info(" DELETE 권한없음 전송");
        return "redirect:/test/{id}";
    }

    @PostMapping("/test/commentAddLike")
    @ResponseBody
    public String commentAddLike(HttpServletRequest request,@RequestBody Anne anne
                                            ,RedirectAttributes rttr)
    // param으로 한번 받아보고 안되면 static class
    {
        /**  중복인 경우- 0을 리턴
         *   권한 없는 경우(로그인하지 않음)- "좋아요 권한 없음" 리턴
         *   정상- 1 리턴
         *
         *   숫자 하나를 제외하고는 String 리턴 시 오류 발생. 왜 0~9 만 가능할까?
         */
        log.info(" *********받아온 CommentId는 {}", anne.getCommentId());
        // 닉네임 구하기
        HttpSession session = request.getSession();
        String userName = (String)session.getAttribute("userSession");

        log.info("********* 세션에서 ID 확인 == {} ***", userName);
        Long commentIdL = anne.getCommentId();

        TestComment comment = em.find(TestComment.class, commentIdL);
        JsonObject obj = new JsonObject();
        if( userName==null ){
            // Json 생성을 도와주는 GSON 설치
            obj.addProperty("rspLike","좋아요 권한 없음. 로그인 하세요");
            return obj.toString();
            // 좋아요 권한 없음 오류. 숫자로 보내지 않으면 파싱에러?
        }

        // 세션에 등록된 ID가 있으면 if문을 지나 여기까지 오게 됨.
        boolean result = commentService.likes_isSameUser(commentIdL, userName);
        log.info("*******{}***********", result);

        if( result ){
            obj.addProperty("rspLike","좋아요는 한 번만");
            return obj.toString();
        } else {
            //이제 좋아요 +1
            // 그리고 Likes 엔티티 생성해서 정보 넣고 persist
            commentService.addLikes(commentIdL,userName);
            obj.addProperty("rspLike","좋아요 정상 처리");
            return obj.toString();
        }

    }
    static class Anne{
        private Long commentId;

        public Long getCommentId() {
            return commentId;
        }

        public void setCommentId(Long commentId) {
            this.commentId = commentId;
        }
    }













}




















