package com.nigagara.hawaii.controller;

import com.google.gson.JsonObject;
import com.nigagara.hawaii.entity.*;
import com.nigagara.hawaii.service.CommentService;
import com.nigagara.hawaii.service.TestService;
import com.nigagara.hawaii.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

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
    private final UserService userService;


    @Transactional // 데이터 변경
    @GetMapping("/test/{id}")
    public String showTestView(@PathVariable Long id, Model model,
                                HttpServletRequest request, HttpServletResponse  response)
    {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("userSession");
        // 최근 본 테스트 데이터 쌓기
        if(username!=null){
            User user = userService.findByUserName(username); // 영속
            TestEntity testEntity = em.find(TestEntity.class, id);


            RecentTest rTest = new RecentTest();
            rTest.setUser(user);
            rTest.setRecentTestName(testEntity.getTestName());
            rTest.setRecentTestUrl("/test/"+id);
            em.persist(rTest);
        }


        /**쿠키를 get해서 비어있는지 확인, 비어있으면 새 쿠키 생성해주고(rsps.addCookie)
         *  로직 실행
         * 비어있지 않으면 쿠키 중에 카운트 쿠키가 있는지 확인
         *   있으면 그냥 보여주기만 하고 로직 실행
         *   카운트 쿠키가 없으면  쿠키 쥐어주고 조회수 올리기  */
        //조회수 전용 cookieName 확인용 변수. 적합한 쿠키를 가지고 있으면 0이 된다.
        int cook = 1;
        // 테스트 번호 PathVar
        TestEntity testEntity = em.find(TestEntity.class, id);

        String cookieView = "cookieView"+id; // 경로변수 id와 합한 쿠키명 변수

        Cookie[] cookies = request.getCookies();
        if( cookies == null ){ //  cookies 가 비어있으면?
            log.info("쿠키가 비어있어 새로 생성");
            cook=0;
            testEntity.setView(testEntity.getView()+1); // @PathVar 조회수 +1 Dirty Checking
            Cookie cookie = new Cookie(cookieView, "cookieViewVal");
            response.addCookie(cookie);
            log.info("{}", cookie);
        } else { // 쿠키는 있으나 조회수 전용 cookie name 이 아닐 경우 아래의 if(cook==1)에서 걸리고
                    // 위의 빈 쿠키 로직 실행
            for(int i = 0; i < cookies.length; i++){
                if(cookies[i].getName().equals(cookieView)){
                    log.info("쿠키가 이미 있어 생성 않음");
                    cook = 0;
                    System.out.println("cookie_view를 가지고 있어 일반 로직 진행");
                }
            }
        }
        if(cook==1){ // 쿠키는 있으나 정확한 값이 아닐 때
            log.info("쿠키가 비어있지 않으나 정확한 쿠키 key값이 아님");
            testEntity.setView(testEntity.getView()+1); // @PathVar 조회수 +1 Dirty Checking
            Cookie cookie = new Cookie(cookieView, "cookieViewVal");
            response.addCookie(cookie);
        }
        // 데이터가 없으면 null
        // 표시할 댓글을 가져옴
        List<TestComment> comments = commentService.findComments(testEntity);

        model.addAttribute("comments", comments);
        model.addAttribute("testId", id);
        model.addAttribute("testName", testEntity.getTestName());

        // 조회수 순위. sort메서드 실행해 반환한 후 모델에 넣음
        List<TestEntity> sortedTest = testService.sortTests();
        model.addAttribute("sorted", sortedTest);
        model.addAttribute("path", id);
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
        //id를 이용한 crud- C
        // showTestView에서 모델에 실려가는 sortedTest에 반영되게끔
        // TestEntity id를 찾아서 다대일 매핑을 이용해 TestComment를 persist하고 끝낸다

        CommentData data = new CommentData(userName, textBox, 0L);
        TestComment comment = new TestComment();
        comment.setCommentData(data);
        comment.setTestEntity(em.find(TestEntity.class, id));
        em.persist(comment);

        return "redirect:/test/"+id;
    }

    @PostMapping("/test/updateComment")
    @Transactional
    @ResponseBody
    public String updateComment( @RequestBody UpdateDTO updateDTO
                                , HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userSession");
        /** 해당 번호 글의 댓글들을 리스트로 모두 불러와
         *   행 번호로 특정 댓글을 찾아내는 방식
         *   -> 댓글 식별자를 이용해 하나의 댓글만 찾아와 수정하도록 변경.
         */
        // 2줄로 끝
        TestComment comment = em.find(TestComment.class, updateDTO.getCommentId());
        if( (comment.getCommentData().getUserName()).equals(userName) ){
            comment.getCommentData().setContent(updateDTO.getContent());

            JsonObject obj = new JsonObject();
            obj.addProperty("updateResult", "수정 정상 처리");
            return obj.toString();
        }
        JsonObject obj = new JsonObject();
        obj.addProperty("updateResult", "수정 권한 없음");
        log.info(" UPDATE 권한없음 전송");
        return obj.toString();
    }
    @Getter
    static class UpdateDTO{
        Long commentId;
        String content;
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
    public String commentAddLike(HttpServletRequest request,@RequestBody CommentId commentId)
    {
        /**  중복인 경우- 0을 리턴
         *   권한 없는 경우(로그인하지 않음)- "좋아요 권한 없음" 리턴
         *   정상- 1 리턴
         *
         *   숫자 하나를 제외하고는 String 리턴 시 오류 발생. 왜 0~9 만 가능할까?
         */
        log.info(" *********받아온 CommentId는 {}", commentId.getCommentId());
        // 닉네임 구하기
        HttpSession session = request.getSession();
        String userName = (String)session.getAttribute("userSession");

        log.info("********* 세션에서 ID 확인 == {} ***", userName);
        Long commentIdL = commentId.getCommentId();

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
    static class CommentId{
        private Long commentId;

        public Long getCommentId() {
            return commentId;
        }

        public void setCommentId(Long commentId) {
            this.commentId = commentId;
        }
    }

}









//    @PostMapping("/test/{id}/updateComment")
//    @Transactional
//    public String updateComment(@PathVariable Long id, @RequestParam("textBoxU") String content,
//                                @RequestParam("commId") Long commentId, HttpServletRequest request
//            ,Model model, RedirectAttributes rttr)
//    {
//        HttpSession session = request.getSession();
//        String userName = (String) session.getAttribute("userSession");
//        /** 해당 번호 글의 댓글들을 리스트로 모두 불러와
//         *   행 번호로 특정 댓글을 찾아내는 방식
//         *   -> 댓글 식별자를 이용해 하나의 댓글만 찾아와 수정하도록 변경.
//         */
//        // 2줄로 끝
//        TestComment comment = em.find(TestComment.class, commentId);
//        if( (comment.getCommentData().getUserName()).equals(userName) ){
//            comment.getCommentData().setContent(content);
//            return "redirect:/test/{id}";
//        }
//        rttr.addFlashAttribute("authError", "수정 권한 없음");
//        log.info(" UPDATE 권한없음 전송");
//        return "redirect:/test/{id}";
//    }
//    @ResponseBody
//    @PostMapping("/test/deleteComment")
//    @Transactional
//    public String deleteComment(@RequestBody CommentId commentId
//            , HttpServletRequest request)
//    {
//        HttpSession session = request.getSession();
//        String  username = (String) session.getAttribute("userSession");
//
//        TestComment comment = em.find(TestComment.class, commentId.getCommentId());
//        if( (comment.getCommentData().getUserName()).equals(username) ){
//
//            // 댓글 삭제 전에 댓글 대상의 좋아요를 모두 빼낸다. // 좋아요의 PK로 조회하는 것이 아니므로 delete JPQL
//            Query deleteQuery = em.createQuery("delete from Likes  L where  " +
//                    "L.testComment =:deleteComment")
//                    .setParameter("deleteComment", comment);
//            deleteQuery.executeUpdate(); //Execute an update or delete statement.
//            em.remove(comment);
//
//            JsonObject obj = new JsonObject();
//            obj.addProperty("respDelete", "삭제 정상 처리");
//            log.info("DELETE 정상 처리 전송");
//            return obj.toString();
//        }
//        // 세션에서 얻은 id와 댓글의 id가 같지 않음
//        JsonObject obj = new JsonObject();
//        obj.addProperty("respDelete", "삭제 권한 없음");
//        log.info(" DELETE 권한없음 전송");
//        return obj.toString();
//    }