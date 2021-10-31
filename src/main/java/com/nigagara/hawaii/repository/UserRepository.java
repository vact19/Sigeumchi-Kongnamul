package com.nigagara.hawaii.repository;

import com.nigagara.hawaii.entity.RecentTest;
import com.nigagara.hawaii.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
@Data
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
        User findUser = ByUserName(user.getUserName());
        if( findUser!=null ){
            throw new IllegalStateException("=============유저 중복=============");
        }
        System.out.println(" ValidateUser 중복 없음 ========================");
    }

    public User ByUserName(String name) {
        try {
            return em.createQuery("select u from User u where u.userName =:name", User.class)
                    .setParameter("name", name).getSingleResult();
        } catch (Exception e){
            log.info(" {} 검색결과 유저 테이블에서 발견되지 않음", name);
            return null;
        }
        // List로 받지 않으면 못 찾았을 때 오류 발생
    }

    public List<User> find_All(){
        return em.createQuery("select u from User  as u ", User.class).getResultList();
    }


    public List<RecentTest> recentTest(User user) {
        // spring data jpa 있으면 findAll() 구현 안해도 될 듯
        return em.createQuery("select RT FROM RecentTest as RT WHERE " +
                "RT.user=:user", RecentTest.class)
                .setParameter("user", user)
                .getResultList();
    }
}
