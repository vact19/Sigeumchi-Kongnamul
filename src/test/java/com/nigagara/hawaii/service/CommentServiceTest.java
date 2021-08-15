package com.nigagara.hawaii.service;

import com.nigagara.hawaii.entity.CommentData;
import com.nigagara.hawaii.entity.TestComment;
import com.nigagara.hawaii.entity.TestEntity;
import com.nigagara.hawaii.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 스프링과 JPA를 활용한 테스트를 하기 위한 Annotation 3가지
@Slf4j
public class CommentServiceTest {

    @Autowired CommentService commentService;
    @Autowired CommentRepository commentRepository;
    @Autowired EntityManager em;


    @Test
    public void 댓글등록() throws Exception{
        //given
        TestEntity testEntity = new TestEntity();
        testEntity.setTestName("뚱이에요");
        em.persist(testEntity);
        Long testId = testEntity.getId();

        TestEntity testEntity2 = new TestEntity();
        testEntity.setTestName("뚱이라니까요");
        em.persist(testEntity2);
        Long testId2 = testEntity2.getId();

        TestComment comment = new TestComment();
        CommentData commentData = new CommentData("다람쥐", "월요일 좋아", 4L);
        TestComment comment2 = new TestComment();
        CommentData commentData2 = new CommentData("다람이", "월요일 조으아", 44L);

        comment.setCommentData(commentData);
        comment.setTestEntity(em.find(TestEntity.class, testId));
        comment2.setCommentData(commentData2);
        comment2.setTestEntity(em.find(TestEntity.class, testId2));

        //when
        Long savedId = commentService.saveComment(comment);
        Long savedId2 = commentService.saveComment(comment2);
        //then
        System.out.println(savedId2);
        Assertions.assertThat(savedId).isEqualTo(1);
        Assertions.assertThat(savedId).isEqualTo(testEntity.getId());
        Assertions.assertThat(savedId2).isEqualTo(2);
        Assertions.assertThat(savedId2).isEqualTo(testEntity2.getId());
        /**
         * 댓글 등록 테스트 완료
         */
     }

     @Test
     public void  댓글출력() throws Exception{
         //given
         TestEntity testEntity = new TestEntity();
         testEntity.setTestName("뚱이에요");
         em.persist(testEntity);
         Long testId = testEntity.getId();

         TestEntity testEntity2 = new TestEntity();
         testEntity2.setTestName("뚱이라니까요");
         em.persist(testEntity2);
         Long testId2 = testEntity2.getId();

         TestComment comment = new TestComment();
         CommentData commentData = new CommentData("다람쥐1", "월요일 좋아1", 4L);
         TestComment comment2 = new TestComment();
         CommentData commentData2 = new CommentData("다람이2", "월요일 조으아2", 44L);
         TestComment comment3 = new TestComment();
         CommentData commentData3 = new CommentData("다람이3", "월요일 안조아3", 444L);
         TestComment comment4 = new TestComment();
         CommentData commentData4 = new CommentData("다람이4", "월요일 안조아4", 444L);
         TestComment comment5 = new TestComment();
         CommentData commentData5 = new CommentData("다람이5", "월요일 안조아5", 444L);

         comment.setCommentData(commentData);
         comment.setTestEntity(em.find(TestEntity.class, testId));
         comment2.setCommentData(commentData2);
         comment2.setTestEntity(em.find(TestEntity.class, testId2));
         comment3.setCommentData(commentData3);
         comment3.setTestEntity(em.find(TestEntity.class, testId2));
         comment4.setCommentData(commentData4);
         comment4.setTestEntity(em.find(TestEntity.class, testId));
         comment5.setCommentData(commentData5);
         comment5.setTestEntity(em.find(TestEntity.class, testId));
         em.persist(comment); em.persist(comment2); em.persist(comment3); em.persist(comment4); em.persist(comment5);

         System.out.println(commentService.removeComment(1L));
         List<TestComment> comments1 = commentService.findComments(testEntity);
         List<TestComment> comments2 = commentService.findComments(testEntity2);



         //then
         for (TestComment testComment : comments2) {
             System.out.println("testComment.getTestEntity() = " + testComment.getTestEntity().getTestName());
             System.out.println("testComment = " + testComment.getCommentData().getContent());
             System.out.println("testComment = " + testComment.getId());
         }
         for (TestComment testComment : comments1) {
             System.out.println("testComment.getTestEntity() = " + testComment.getTestEntity().getTestName());
             System.out.println("testComment = " + testComment.getCommentData().getContent());
             System.out.println("testComment = " + testComment.getId());
         }


         // 출력 테스트 끝





      }


}















