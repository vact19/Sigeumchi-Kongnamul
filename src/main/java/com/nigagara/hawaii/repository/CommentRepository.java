package com.nigagara.hawaii.repository;

import com.nigagara.hawaii.entity.TestComment;
import com.nigagara.hawaii.entity.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

   private final EntityManager em;

   public void save(TestComment comment){
        em.persist(comment);
   }

   public List<TestComment> findAll(TestEntity testEntity){
       return em.createQuery("select tc from TestComment tc join tc.testEntity where " +
               "  tc.testEntity = :testEntity", TestComment.class)
               .setParameter("testEntity", testEntity).getResultList();
   }


    public Long remove(Long id) {
        TestComment comment = em.find(TestComment.class, id);
        Long idToReturn = comment.getId();
        em.remove(comment);
        return idToReturn;
    }
}
