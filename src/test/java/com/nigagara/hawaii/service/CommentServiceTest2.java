package com.nigagara.hawaii.service;


import com.nigagara.hawaii.entity.CommentData;
import com.nigagara.hawaii.entity.Likes;
import com.nigagara.hawaii.entity.TestComment;
import com.nigagara.hawaii.entity.TestEntity;
import com.nigagara.hawaii.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 스프링과 JPA를 활용한 테스트를 하기 위한 Annotation 3가지
@Slf4j

public class CommentServiceTest2 {

    @Autowired CommentService commentService;
    @Autowired CommentRepository commentRepository;
    @Autowired EntityManager em;


    @Test
    public void  좋아요_DB테스트() throws Exception{

        /**
         *   테스트 성공. commentService의 likes_isSameUser,
         *                                                  addLikes 정상 동작
         */
        TestEntity testEntity = new TestEntity();
        em.persist(testEntity);

        TestComment comment = new TestComment();
        comment.setTestEntity(testEntity);
        comment.setCommentData(new CommentData("rla","anne",11L));
        em.persist(comment);

        Likes likes = new Likes(comment, "rla");
        em.persist(likes);
        // persist 일일히 안하면 Object references unsaved transient... 뜸

        boolean result = commentService.likes_isSameUser(1L, "rla");

          Assertions.assertThat(result).isEqualTo(false);

        // addLikes 정상 동작
        commentService.addLikes(1L, "rla");
        Long likes1 = comment.getCommentData().getLikes();
        System.out.println("likes1 = " + likes1);
    }

}






















