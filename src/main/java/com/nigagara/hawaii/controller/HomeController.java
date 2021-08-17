package com.nigagara.hawaii.controller;

import com.nigagara.hawaii.entity.CommentData;
import com.nigagara.hawaii.entity.TestComment;
import com.nigagara.hawaii.entity.TestEntity;
import com.nigagara.hawaii.entity.User;
import com.nigagara.hawaii.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final EntityManager em;
    private final UserService userService;

    @GetMapping("/") // 모든 방식에 반응
    public String home(){
        return "home";
    }

    @GetMapping("/users")
    public String showUserList(Model model){

        List<User> users = userService.findUsers();
        model.addAttribute("users",users);
        return "/user/userList"; // userList.html
    }

    @PostMapping("/generatedata")
    @Transactional
    public String generateUser(){
        generateUserTest();
       generateCommentAndTest(); // 기본 테스트 1, 2와  기본 댓글 생성
        return "redirect:/";
    }

    public void generateCommentAndTest() {
        // Test는 생성 메서드가 없음. 직접 @Transactional persist
        TestEntity testEntity = new TestEntity();
        testEntity.setTestName("1뚱이에요1"); testEntity.setCount(9);
        em.persist(testEntity);
        Long testId = testEntity.getId();

        TestEntity testEntity2 = new TestEntity();
        testEntity2.setTestName("2뚱이라니까요2"); testEntity2.setCount(7);
        em.persist(testEntity2);
        Long testId2 = testEntity2.getId();

        TestEntity testEntity3 = new TestEntity();
        testEntity3.setTestName("3뚱이라니까요3"); testEntity3.setCount(5);
        em.persist(testEntity3);
        Long testId3 = testEntity3.getId();

        TestEntity testEntity4 = new TestEntity();
        testEntity4.setTestName("4뚱이라니까요4"); testEntity4.setCount(11);
        em.persist(testEntity4);
        Long testId4 = testEntity4.getId();

        TestComment comment = new TestComment();
        CommentData commentData = new CommentData("다람쥐1", "월요일 좋아1", 4L);
        TestComment comment2 = new TestComment();
        CommentData commentData2 = new CommentData("다람이2", "월요일 안조아2", 44L);
        TestComment comment3 = new TestComment();
        CommentData commentData3 = new CommentData("다람이3", "월요일 안조아3", 444L);
        TestComment comment4 = new TestComment();
        CommentData commentData4 = new CommentData("다람이4", "월요일 안조아4", 4444L);
        TestComment comment5 = new TestComment();
        CommentData commentData5 = new CommentData("다람이5", "월요일 안조아5", 44444L);

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
        user1.setUserName("JO1");
        user1.setEmail("etrete@.com");
        user1.setPassword("12345");

        User user2 = new User();
        user2.setUserName("JO2");
        user2.setEmail("gdgdg@.com");
        user2.setPassword("12345");

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

















