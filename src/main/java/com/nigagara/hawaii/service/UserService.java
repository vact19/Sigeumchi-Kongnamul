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
            System.out.println(" 그런 사람 없어요 ");
        //Optional<String> userName1 = Optional.ofNullable(user.getUserName());



        if( user.getUserName().equals(userName) && user.getPassword().equals(password)) {
            return LoginResult.SUCCESS;
        } else if ( user.getUserName().equals(userName) &&
                !(user.getPassword().equals(password)) ){
            return LoginResult.ONLYID;
        } else if ( !(user.getUserName().equals(userName)) &&
                user.getPassword().equals(password) ) {
            return LoginResult.ONLYPASSWORD;
        } else if ( !(user.getUserName().equals(userName)) &&
                !(user.getPassword().equals(password)) ) {
            return LoginResult.FAIL;
        }



        return null;
    }


//    public User findByUserName(String name){
//        return userRepository.ByUserName(name);
//    }



}
