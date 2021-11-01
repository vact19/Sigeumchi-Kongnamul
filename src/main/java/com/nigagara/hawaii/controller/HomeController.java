package com.nigagara.hawaii.controller;
import com.nigagara.hawaii.controller.DTO.LoginFormDTO;
import com.nigagara.hawaii.entity.*;
import com.nigagara.hawaii.repository.TestRepository;
import com.nigagara.hawaii.repository.Test_Repository;
import com.nigagara.hawaii.repository.UserRepository;
import com.nigagara.hawaii.repository.User_Repository;
import com.nigagara.hawaii.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.persistence.EntityManager;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {


    // 구현체 JavaMailSenderImpl이 자동 주입됨
    private final EntityManager em;
    private final UserService userService;
    private final PasswordEncoder encoder;
    private final TestRepository testRepository;
    private final User_Repository user_repository;
    private final Test_Repository test_repository;

    final String TYPE1="type1";
    final String TYPE2="type2";
    final String TYPE3="type3";

    @GetMapping("/bb")
    public String home(){
        return "bb";
    }

    /**
     *spring security는 로그아웃 시 /login?logout으로 get 요청
     *       *  logout() 파라미터 설정을 안해서 기본 경로인 /logout 로그아웃 경로,
     *      *로그아웃 후 경로가 /login?logout으로 잡힌 듯 -> 경로를 /user/logout으로 설정하니
     *      *spring security가 안 먹힘.
     */
    @GetMapping({"/", "/login"})
    public String home(Model model,
                                    @RequestParam(required = false) String byName, // 검색 파라미터
                                    @RequestParam(required = false) String byType
                                    ,@PageableDefault(size = 8) Pageable pageable
                                    ,@RequestParam(required = false, defaultValue = "") String searchText
                                    ){
        // 빈 폼 전달하지 않으면 오류 발생
        model.addAttribute("loginFormDTO", new LoginFormDTO());

        //페이징
        Page<TestEntity> te = test_repository.findByTestNameContainingOrTestTypeContaining(searchText, searchText, pageable) ;
        int startPage = Math.max(1, te.getPageable().getPageNumber() - 4); // 시작 페이지. 0 이하로 내려가지 않도록 max()사용
        int endPage = Math.min(te.getTotalPages(), te.getPageable().getPageNumber() + 4);
        //1; , users.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("testPage",te);
        return "home";

        // 페이징+검색 기능 추가로 기존 검색로직 삭제
        //Test 검색에 사용되는 Get조회요청에 따른 응답
//        if(StringUtils.hasText(byName)){
//            List<TestEntity> tests = testRepository.searchByName(byName);
//            model.addAttribute("tests",tests);
//            return "home";
//        } else if(StringUtils.hasText(byType)){
//            List<TestEntity> tests = testRepository.searchByType(byType);
//            model.addAttribute("tests",tests);
//            return "home";
//        } else{
//        List<TestEntity> tests = testRepository.findAll();
//        model.addAttribute("tests",tests);
//        return "home";
//        }
    }
    // 최근 탐색 테스트 목록
    @GetMapping("/recentTest")
    public String showRecentTest(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("userSession");
        // 해당 유저의 DB ID값을 구해서, ID로 최근테스트 테이블의 자료를 조회해 리스트로.
        User user = userService.findByUserName(username);

        List<RecentTest> recentTest = userService.findRecentTest(user);
        Collections.reverse(recentTest); // 순차적으로 쌓인 리스트를 뒤집기
        model.addAttribute("recentTest", recentTest);

        return "/user/recentTestList";
    }

    // user 목록
    @GetMapping("/users")
    public String showUserList(Model model, @PageableDefault(size = 2) Pageable pageable
                                        , @RequestParam(required = false, defaultValue = "") String searchText){ //size 기본값 2
        //Page<User> users = user_repository.findAll(PageRequest.of(0,20)); // 0부터 시작
        //Page<User> users = user_repository.findAll(pageable); // 0부터 시작
        Page<User> users = user_repository.findByUserNameContainingOrEmailContaining(searchText, searchText, pageable );
        int startPage = Math.max(1, users.getPageable().getPageNumber() - 4); // 시작 페이지. 0 이하로 내려가지 않도록 max()사용
        int endPage = Math.min(users.getTotalPages(), users.getPageable().getPageNumber() + 4);
        //1; , users.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("users",users);
        return "/user/userList"; // userList.html
    }


    // 랜덤 탐색
    @PostMapping("/random")
    public String randomTest(){
        Random rand = new Random();
        int pathVar = rand.nextInt(4)+1; // 1~4

        // 형변환 자동.
        return "redirect:/test/"+pathVar;
    }

    // 데이터 생성
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
        testEntity.setTestName("스마트폰 중독 테스트"); testEntity.setView(9);
        testEntity.setTestType(TYPE1);
        em.persist(testEntity);
        Long testId = testEntity.getId();

        TestEntity testEntity2 = new TestEntity();
        testEntity2.setTestName("내게 맞는 프로그래밍 언어"); testEntity2.setView(7);
        testEntity2.setTestType(TYPE2);
        em.persist(testEntity2);
        Long testId2 = testEntity2.getId();

        TestEntity testEntity3 = new TestEntity();
        testEntity3.setTestName("내향성 테스트"); testEntity3.setView(5);
        testEntity3.setTestType(TYPE3);
        em.persist(testEntity3);
        Long testId3 = testEntity3.getId();

        TestEntity testEntity4 = new TestEntity();
        testEntity4.setTestName("4번 테스트"); testEntity4.setView(11);
        testEntity4.setTestType(TYPE3);
        em.persist(testEntity4);
        Long testId4 = testEntity4.getId();

        TestEntity testEntity5 = new TestEntity();
        testEntity5.setTestName("4번 테스트"); testEntity5.setView(11);
        testEntity5.setTestType(TYPE3);
        em.persist(testEntity5);
        Long testId5 = testEntity5.getId();

        TestEntity testEntity6 = new TestEntity();
        testEntity6.setTestName("4번 테스트"); testEntity6.setView(11);
        testEntity6.setTestType(TYPE3);
        em.persist(testEntity6);
        Long testId6 = testEntity6.getId();

        TestEntity testEntity7 = new TestEntity();
        testEntity7.setTestName("4번 테스트"); testEntity7.setView(11);
        testEntity7.setTestType(TYPE3);
        em.persist(testEntity7);
        Long testId7 = testEntity7.getId();

        TestEntity testEntity8 = new TestEntity();
        testEntity8.setTestName("4번 테스트"); testEntity8.setView(11);
        testEntity8.setTestType(TYPE3);
        em.persist(testEntity8);
        Long testId8 = testEntity8.getId();

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
        user1.setEmail("chj6703@naver.com");

        String encodedPwd = encoder.encode("3"); // salting 처리까지 자동
        user1.setPassword(encodedPwd);

        user1.setPwdQuestion("내 보물 1호는?"); user1.setPwdAnswer("이 웹사이트");
        user1.setPwdHint(user1.getUserName()+"의 힌트");


        User user2 = new User();
        user2.setUserName("jo2");
        user2.setEmail("gdgdg@.com");

        String encodedPwd2 = encoder.encode("22");
        user2.setPassword(encodedPwd2);

        user2.setPwdQuestion("내가 처음 산 차는?"); user2.setPwdAnswer("차가 없음");
        user2.setPwdHint(user2.getUserName()+"의 힌트2");

        User user3 = new User();
        user3.setUserName("jo3");
        user3.setEmail("gxvsdf@.com");
        user3.setPassword("33");

        User user4 = new User();
        user4.setUserName("jo4");
        user4.setEmail("gwefsd@.com");
        user4.setPassword("44");

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

















