package com.nigagara.hawaii.controller;

import com.nigagara.hawaii.controller.DTO.*;
import com.nigagara.hawaii.entity.User;
import com.nigagara.hawaii.repository.UserRepository;
import com.nigagara.hawaii.service.LoginResult;
import com.nigagara.hawaii.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    @Autowired SmartValidator validator;
    private final UserService userService;
    private final UserRepository userRepository;
    private final EntityManager em;
    private final PasswordEncoder encoder; //


    @GetMapping("/user/new")
    public String createUser(Model model){
        model.addAttribute("userFormDTO", new UserFormDTO());
        model.addAttribute("typeMismatchTest", new TypeMismatchTest());
        //model로 빈 객체 넘겨서 표시.  주소창 단순입력으로 접근 불가
        return "user/createUserForm";

    }

    @GetMapping("/user/findPwd")
    public String showFindPwd(Model model
                                    , FindPwdDTO findPwdDTO, emailDTO emailDTO){
        /**
         *  세션에서 이름을 가져와서 유저테이블을 조회하고,
         *   유저테이블의 pwdQuestion 정보를 DTO에 넣어서 모델에 넣어 보냄
         */
        /** RedirectAttribute.의 AddFlashAttribute.에 User,객체가 담겨 옴
         *  model 에서 꺼내서 사용.
         */
        User user = (User) model.getAttribute("user");
        if(user !=null){
            findPwdDTO.setPwdQuestion( user.getPwdQuestion() );
            findPwdDTO.setUsername( user.getUserName() );
            emailDTO.setUsername( user.getUserName() );
            return "user/findPwd";
        }

        return "user/findPwd";
    }

    @Transactional
    @PostMapping("/user/findPwdByEmail")
    public String findPwdByMail(@Valid emailDTO form, BindingResult bindingResult
                        ,FindPwdDTO findPwdDTO, Model model){
        if(bindingResult.hasErrors()){
            return "user/findPwd";
        }
        /**
         *   email과 ID 제대로 입력했는지 확인
         *   2. 맞으면 난수 생성 후 암호화해 DB에 set
         *   3. 난수를 이메일로 전달
         */
        List<User> resultList = em.createQuery("select U from User  U WHERE " +
                "U.userName=:username AND " +
                "U.Email =: email")
                .setParameter("username", form.getUsername())
                .setParameter("email", form.getEmail())
                .getResultList();
        if (resultList.isEmpty()){
            bindingResult.reject("NoMatch_Id_Email");
            return "user/findPwd";
        }
        /**
         *  임시 비밀번호 발급
         */
        String tempPwd = userService.getRandomPwd(); // 8자리 랜덤 숫자+영소문자 임시 비밀번호 생성
        //이제 DB 업데이트
        User user = resultList.get(0); // JPQL 조회 후 영속 상태
        String encodedPwd = encoder.encode(tempPwd);
        user.setPassword(encodedPwd); // 더티 체크

        //이메일 보내는 코드
        userService.sendEmail(form.getEmail(), tempPwd);

        // 정상 처리 알림
        model.addAttribute("EmailOK", "메일함 확인해요");
        return "user/findPwd";
    }
    static class emailDTO{
        @Email(message = "이메일 형식을 지켜주세요",
                regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
        private String email;   // email 1, 2를 더해서 3 검증. 초기값은 hidden에서 날아옴
        private String username;
        public String getUsername() { return username; }public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; } public void setEmail(String email) { this.email = email; }
    }

    @GetMapping("/user/findPwd_id")
    public String findPwd_id(){
        return "user/findPwd_id";
    }

    // 비밀번호 찾기 화면 이전에 id부터 확인한 뒤 비밀번호 찾기 질문을 뿌려줌
    @PostMapping("/user/findPwdSubmitId")
    public String findPwd_id(Model model, @RequestParam String name, RedirectAttributes rttr){
        User user = userRepository.ByUserName(name);
        if(user==null){
            model.addAttribute("idError", "그런 ID 없어요");
            return "user/findPwd_id";
        }
        rttr.addFlashAttribute("user", user);
        return "redirect:/user/findPwd";
    }
    // 회원 탈퇴 (마이페이지에서)
    @PostMapping("/user/deleteUser")
    @Transactional
    public String deleteUser(HttpServletRequest request){
        // 클라이언트 쿠키의 정보와 서버가 가지고 있는 정보가 일치하는지 확인
        HttpSession session = request.getSession();
        // 조회 정보를 바탕으로 사용자 이름 username 을 가져옴
        String username = (String) session.getAttribute("userSession");

        // 세션이 만료되었을 경우 오류 방지
        if( username==null){ return "redirect:/"; }
        // DB에서 사용자 데이터를 조회하여 삭제
        User user = userService.findByUserName(username);
        em.remove(user);

        session.invalidate(); // 세션 지워주기
        return "redirect:/";
    }
    //힌트질문 답안 제출
    @PostMapping("/user/findPwdByHint")
    public String findPwd(@Valid FindPwdDTO form, BindingResult bindingResult
                                ,Model model, emailDTO emailDTO){ // 빈 emailDTO 보내줘야함
        if(bindingResult.hasErrors()){
            // ModelAttribute 자동 전달
            // 타입 변환 필드 오류나 Bean Validation 오류 등...
            return "user/findPwd";
        }
        List<User> resultList = em.createQuery("select U from User  U where " +
                "U.pwdQuestion =:question AND " +
                "U.pwdAnswer =:answer AND "+
                "U.userName =:username")
                .setParameter("question", form.getPwdQuestion())
                .setParameter("answer", form.getPwdAnswer())
                .setParameter("username", form.getUsername())
                .getResultList();
        if(resultList.isEmpty()){
            bindingResult.reject("pwdHint_NoMatch");
            return "user/findPwd";
        }
        // 에러 검사 완료
        // 폼이 null 이면 바인딩 오류, 테이블이 null이면 isEmpty()에서 글로벌 오류가 나기 때문에
        // 사용자가 처음부터 값을 입력하지 않은 경우는 글로벌 에러가 나옴
        User user = resultList.get(0);
        model.addAttribute("pwdHint", user.getPwdHint());
        return "user/findPwd";
    }

    //마이페이지 진입
    @GetMapping("/user/myPage")
    public String showMyPage(Model model, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("userSession");
        User myPageUser = userRepository.ByUserName(username);
        UserFormDTO form = new UserFormDTO();
        //찾은 유저정보를 모델에 보낼 form에 복사하기

        /**
         *  Reference Type 이므로 이렇게 다른 메서드 안에서 값을 바꾸고 나서
         *  return 하지 않아도 변경값이 적용됨. 포인터와 같다. 그런데 Integer는?
         */
        setForm(myPageUser, form);
        model.addAttribute("userFormDTO", form);

        return "user/myPage";
    }

    private void setForm(User myPageUser, UserFormDTO form) {
        form.setUserName(myPageUser.getUserName());
        form.setPassword(""); // 암호화된 비밀번호이므로 비워서 보낸다
        form.setPwdQuestion(myPageUser.getPwdQuestion());
        form.setPwdAnswer(myPageUser.getPwdAnswer());
        form.setPwdHint(myPageUser.getPwdHint());
    }

    @Transactional
    @PostMapping("/user/myPage")
    public String updateUserInfo(@Valid UserFormDTOUpdate form, BindingResult bindingResult
                                        , HttpServletRequest request)
    {
        /**
         *   로그인화면의 TypeMisMatch 폼을 검증하는 메서드
         */
        if(bindingResult.hasErrors()){
            return "/user/myPage";
        }
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("userSession");
        // JPQL로 조회한 엔티티는 영속 상태. 다시 find()로 찾을 필요 없음
        User user = userRepository.ByUserName(username);

        user.setUserName(form.getUserName());
        // 암호화해서 set
        String encodedPwd = encoder.encode(form.getPassword());
        user.setPassword(encodedPwd);

        user.setPwdQuestion( form.getPwdQuestion() );
        user.setPwdAnswer( form.getPwdAnswer() ); user.setPwdHint( form.getPwdHint() );


        session.setAttribute("userSession", user.getUserName());
        // 수정 완료
        return "redirect:/";
    }

    @PostMapping("/user/new/typetest")
    public String validatePrice(@Valid TypeMismatchTest typeTest, BindingResult bindingResult,
                                UserFormDTO form){ // 에러떠서 돌아갈때 userForm.도 모델에 담아가야 함
        /**
         *   로그인화면의 TypeMisMatch 폼을 검증하는 메서드
         */
        // Optional 사용해보기
        int total = Optional.ofNullable(typeTest.getPrice1()).orElse(0)
                + Optional.ofNullable(typeTest.getPrice2()).orElse(0);
        if ( total < 10000){
            // GlobalError를 추가하는 reject()
            bindingResult.reject("totalPriceMin", new Object[]{10000, total}, null);
        }

        if (bindingResult.hasErrors()){
            log.info("ERROR? = {}", bindingResult);
            return "user/createUserForm";
        }
        return "redirect:/";
    }

    @PostMapping("/user/new")
    public String createUserPost(@Valid UserFormDTO form, BindingResult bindingResult,
                                 HttpServletRequest request, TypeMismatchTest test, Model model)
    {                                    // 에러떠서 돌아갈때 TypeMismatchTest.도 모델에 담아가야 함
        if(bindingResult.hasErrors()){
            /**
               Form @ModelAttribute 생략. 기본 key 값은 camel case "userFormDTO"
             반드시 빈 객체의 key 값과 Error Return.의  key.값 일치해야 함
             ( "userFormDTO" )
             */
            return "user/createUserForm"; // 파일명이 아니라 주소명으로 보내면 양식 초기화될듯
        }
        /** Email Interceptor preHandle.에서 등록한 email
         */
        form.setEmail(form.getEmail1()+"@"+form.getEmail2());
        /**
         *  회원가입 화면에서 이메일 양식 확인을 하기 위해 두 번 검증함
         */
        validator.validate(form, bindingResult);
        if(bindingResult.hasErrors()){
            return "user/createUserForm";
        }
        User user = setUserField(form, new User());
        userService.joinUser(user);

        return "redirect:/";
    }
    @PostMapping("/user/login")
    public String login(LoginFormDTO form, HttpServletRequest request
                        , Model model, RedirectAttributes redirectAttributes
                        ,@RequestParam(defaultValue = "/") String redirectURL)
    /**
     *  LoginFormDTO 모델에 자동 전송 안되는 문제가 생겨
     *  조건문 분기점마다 add()
     */
    {
        LoginResult result = userService.login(form.getUserName(), form.getPassword());
        System.out.println("result = " + result);
        if ( result == LoginResult.NO_SUCH_ID ){
            //ID.부터 틀렸으니 양식을 비워서 다시 전달
            form.setUserName("");  form.setPassword("");
            model.addAttribute("idError", "그런 id 없어요");
            model.addAttribute("loginFormDTO",form);
            return "home";
        } else if ( result == LoginResult.ONLY_ID ){
            form.setPassword(""); // Password만 비워주고 돌려보냄
            model.addAttribute("pwdError", "PWD 다시 입력");
            model.addAttribute("loginFormDTO",form);
            return "home";
        } else if (result == LoginResult.SUCCESS) {

            HttpSession session = request.getSession(true); // 세션 생성
            session.setMaxInactiveInterval(1000); // 세션 ㅜ초임.
            session.setAttribute("userSession", form.getUserName());

            /** session 객체 모델 전송은 X. 타임리프에서 자동 처리
             */
           // redirect 되는 url에 모델로 보내짐
           redirectAttributes.addFlashAttribute("session11", "rttr.addFlashAttribute()");
           log.info(redirectURL);
           log.info("???");
            return "redirect:"+ redirectURL;
        }
        return "unexpectedError";
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/";
    }






    private User setUserField(UserFormDTO form, User user) {
        user.setUserName(form.getUserName());
        user.setEmail(form.getEmail());

        String encodedPwd = encoder.encode(form.getPassword());
        user.setPassword(encodedPwd);

        user.setPwdQuestion(form.getPwdQuestion());
        user.setPwdAnswer(form.getPwdAnswer());
        user.setPwdHint(form.getPwdHint());
        user.setRegDate(LocalDateTime.now());
        return user;
    }

}


















