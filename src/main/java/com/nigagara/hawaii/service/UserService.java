package com.nigagara.hawaii.service;

import com.nigagara.hawaii.entity.User;
import com.nigagara.hawaii.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager em;

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

        User user = userRepository.findByUserName(userName);
        // 로그인 시도된 파라미터, 기존 User user
        if ( user==null )
            return LoginResult.NO_SUCH_ID; // findByUserName.에서 NULL 반환
        //Optional<String> userName1 = Optional.ofNullable(user.getUserName());

        if( user.getUserName().equals(userName) && user.getPassword().equals(password)) {
            return LoginResult.SUCCESS; // ID와 PWD 모두 일치하면
        } else if ( user.getUserName().equals(userName) &&
                !(user.getPassword().equals(password)) ){
            return LoginResult.ONLY_ID;  // ID만 일치하면
        }
        return null;
    }


//    public User findByUserName(String name){
//        return userRepository.ByUserName(name);
//    }



}
