package com.nigagara.hawaii.controller;

import com.nigagara.hawaii.controller.DTO.LoginFormDTO;
import com.nigagara.hawaii.entity.CommentData;
import com.nigagara.hawaii.entity.TestComment;
import com.nigagara.hawaii.entity.TestEntity;
import com.nigagara.hawaii.entity.User;
import com.nigagara.hawaii.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final EntityManager em;
    private final UserService userService;

    @PostMapping("/ajax")
    public String jax(){
        log.info("jax");

        return null;
    }

    @GetMapping("/test")
    public String goTestPage(){
        return "copy";
    }

    @GetMapping("/")
    public String home(Model model, RedirectAttributes redirectAttributes){

        // 빈 폼 전달하지 않으면 오류 발생
        model.addAttribute("loginFormDTO", new LoginFormDTO());
        return "home";
    }

    @GetMapping("/users")
    public String showUserList(Model model){

        List<User> users = userService.findUsers();
        model.addAttribute("users",users);
        return "/user/userList"; // userList.html
    }

    @PostMapping("/generateData")
    @Transactional
    public String generateUser(){
        generateUserTest();
       generateCommentAndTest(); // 기본 테스트 1, 2와  기본 댓글 생성
        return "redirect:/";
    }
















    public void generateCommentAndTest() {
        // Test는 생성 메서드가 없음. 직접 @Transactional persist
        TestEntity testEntity = new TestEntity();
        testEntity.setTestName("1번 게시물"); testEntity.setCount(9);
        em.persist(testEntity);
        Long testId = testEntity.getId();

        TestEntity testEntity2 = new TestEntity();
        testEntity2.setTestName("2번 게시물"); testEntity2.setCount(7);
        em.persist(testEntity2);
        Long testId2 = testEntity2.getId();

        TestEntity testEntity3 = new TestEntity();
        testEntity3.setTestName("3번 게시물"); testEntity3.setCount(5);
        em.persist(testEntity3);
        Long testId3 = testEntity3.getId();

        TestEntity testEntity4 = new TestEntity();
        testEntity4.setTestName("4번 게시물"); testEntity4.setCount(11);
        em.persist(testEntity4);
        Long testId4 = testEntity4.getId();

        TestComment comment = new TestComment();
        CommentData commentData = new CommentData("다람쥐1", "월요일 좋아1", 4L);
        TestComment comment2 = new TestComment();
        CommentData commentData2 = new CommentData("다람이2", "월요일 안조아2", 44L);
        TestComment comment3 = new TestComment();
        CommentData commentData3 = new CommentData("다람이3", "금요일 안조아3", 444L);
        TestComment comment4 = new TestComment();
        CommentData commentData4 = new CommentData("다람이4", "금요일 조아4", 4444L);
        TestComment comment5 = new TestComment();
        CommentData commentData5 = new CommentData("다람이5", "5번째 댓글", 44444L);

        comment.setCommentData(commentData);
        comment.setTestEntity(em.find(TestEntity.class, testId));
        comment2.setCommentData(commentData2);
        comment2.setTestEntity(em.find(TestEntity.class, testId2));
        comment3.setCommentData(commentData3);
        comment3.setTestEntity(em.find(TestEntity.class, testId2));
        comment4.setCommentData(commentData4);
        comment4.setTestEntity(em.find(TestEntity.class, testId));
        comment5.setCommentData(commentData5);
        comment5.setTestEntity(em.find(TestEntity.class, testId));
        // service 이용하지 않고 바로 넣기
        em.persist(comment);
        em.persist(comment2);
        em.persist(comment3);
        em.persist(comment4);
        em.persist(comment5);
    }

    private void generateUserTest() {

        User user1 = new User();
        user1.setUserName("3");
        user1.setEmail("etrete@.com");
        user1.setPassword("3");
        user1.setPwdQuestion("내 보물 1호는?"); user1.setPwdAnswer("이 웹사이트");
        user1.setPwdHint(user1.getUserName()+"의 힌트");


        User user2 = new User();
        user2.setUserName("JO2");
        user2.setEmail("gdgdg@.com");
        user2.setPassword("12345");
        user2.setPwdQuestion("내가 처음 산 차는?"); user2.setPwdAnswer("차가 없음");
        user2.setPwdHint(user2.getUserName()+"의 힌트2");

        User user3 = new User();
        user3.setUserName("JO3");
        user3.setEmail("gxvsdf@.com");
        user3.setPassword("3333");

        User user4 = new User();
        user4.setUserName("JO4");
        user4.setEmail("gwefsd@.com");
        user4.setPassword("4444");

        userService.joinUser(user1);
        userService.joinUser(user2);
        userService.joinUser(user3);
        userService.joinUser(user4);
    }

    //    @GetMapping("/{id}")
//    public String clickTest(@PathVariable String id){ // String id
//        return "home";
//    }



}

















