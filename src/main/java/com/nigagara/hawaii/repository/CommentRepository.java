package com.nigagara.hawaii.repository;

import com.nigagara.hawaii.entity.TestComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

   private final EntityManager em;

   public void save(TestComment comment){
        em.persist(comment);
   }


}
