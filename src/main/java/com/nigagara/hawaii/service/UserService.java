package com.nigagara.hawaii.service;

import com.nigagara.hawaii.entity.User;
import com.nigagara.hawaii.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager em;

    @Transactional
    public Long joinUser(User user){
        userRepository.join(user);
        return user.getId();
    }



}
