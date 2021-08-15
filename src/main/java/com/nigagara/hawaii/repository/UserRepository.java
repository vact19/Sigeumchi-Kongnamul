package com.nigagara.hawaii.repository;

import com.nigagara.hawaii.entity.User;
import com.nigagara.hawaii.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    // @PersistenceContext
    private final EntityManager em;

    // 들어온 유저에 대한 회원가입
    public void join(User user){
        validateUser(user);
        em.persist(user);
         //  find (Entity Type, PK)
        // if the entity instance is contained in the persistence context, it is returned from there. 그리고 PK는 즉시 생성
    }


    //
    private void validateUser(User user) {
        List<User> findUser = findByUserName(user.getUserName());
        if( ! findUser.isEmpty()){
            throw new IllegalStateException("=============유저 중복=============");
        }
        System.out.println(" ValidateUser 중복 없음 ========================");
    }

    public List<User> findByUserName(String name) {
        return em.createQuery("select u from User u where u.userName =:name", User.class)
                .setParameter("name", name).getResultList();
    }

    public List<User> findAll(){
        return em.createQuery("select u from User  as u ", User.class).getResultList();
    }

}
