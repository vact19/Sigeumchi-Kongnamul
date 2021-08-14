package com.nigagara.hawaii.service;

import com.nigagara.hawaii.entity.User;
import com.nigagara.hawaii.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 스프링과 JPA를 활용한 테스트를 하기 위한 Annotation 3가지
@Slf4j
public class UserServiceTest {

    @Autowired UserRepository userRepository;
    @Autowired UserService userService;
    @Autowired EntityManager em;

    //@Test
    @Test(expected = IllegalStateException.class) // 예외가 발생해야 성공
     public void 회원중복() throws Exception{
         //given
        User user = new User();
        user.setUserName("JO");
        user.setEmail("@.com");
        user.setPassword("qwer1234");
        user.setPwdHint("PWDHint");
        user.setPwdQuestion("PWDQQQ");
        user.setPwdAnswer("PWDAAAA");
        user.setRegDate(LocalDateTime.now());

        User user2 = new User();
        user.setUserName("JO2");
        user.setEmail("@.com");

        User user3 = new User();
        user3.setUserName("JO2");

        //when
        Long joinedId = userService.joinUser(user);
        Long joinedId2 = userService.joinUser(user2);
        Long joinedId3 = userService.joinUser(user3);

        //then
        log.info("{}===================", joinedId3);
        Assertions.assertThat(joinedId).isEqualTo(user.getId());
        Assertions.assertThat(joinedId2).isEqualTo(2);

        /**
         *  회원가입과 이름 중복체크 완료
         */
      }



}