package com.nigagara.hawaii.service;

import com.nigagara.hawaii.controller.UserController;
import com.nigagara.hawaii.entity.User;
import com.nigagara.hawaii.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager em;
    private final PasswordEncoder encoder;
    private final JavaMailSender mailSender;

    public String getRandomPwd() {
//       8자리 숫자+영소문자
//       0,1에 따라 숫자와 영소문자 분기
        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);
        StringBuilder sb = new StringBuilder(); // Builder.는 단일스레드, Buffer.는 멀티스레드
        for (int i=0; i<8 ; i++){
            int val = rand.nextInt(2); // 0 혹은 1
            if (val == 0){ // 숫자 생성
                sb.append(String.valueOf( rand.nextInt(10) ));
            } else{ // 소문자 생성
                // ASCII 범위 97~122까지 .
                sb.append(String.valueOf((char) ((rand.nextInt(26)) + 97)));
            }
        }
        return sb.toString(); // 임시 8자리 비밀번호
    }

    // 로그인 + validate 후 등록
    @Transactional
    public Long joinUser(User user){
        userRepository.join(user);
        return user.getId();
    }

    public List<User> findUsers(){
        return userRepository.findAll();
    }

    public LoginResult login(String userName, String password){

        User user = userRepository.ByUserName(userName);
        // 로그인 시도된 파라미터, 기존 User user
        if ( user==null )
            return LoginResult.NO_SUCH_ID; // findByUserName.에서 NULL 반환
        //Optional<String> userName1 = Optional.ofNullable(user.getUserName());

        if( user.getUserName().equals(userName) &&  encoder.matches(password, user.getPassword())) {
            return LoginResult.SUCCESS; // ID와 PWD 모두 일치하면
        } else if ( user.getUserName().equals(userName) &&
                ! encoder.matches(password, user.getPassword()) ){
            return LoginResult.ONLY_ID;  // ID만 일치하면
        }
        return null;
    }


    public User findByUserName(String name){
        return userRepository.ByUserName(name);
    }


    public void sendEmail(String email, String tempPwd) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("WhoAmI의 임시 비밀번호를 발급해 드려요");
        message.setText("받아라  "+ tempPwd);
        mailSender.send(message);
    }
}
