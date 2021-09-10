package com.nigagara.hawaii.repository;

import com.nigagara.hawaii.entity.CommentData;
import com.nigagara.hawaii.entity.Likes;
import com.nigagara.hawaii.entity.TestComment;
import com.nigagara.hawaii.entity.TestEntity;
import com.nigagara.hawaii.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Likes likes_isSame(Long commentId, String userName) {

        /**좋아요 테이블의 FK가 TestComment 대상 다대일 매핑중이므로,
         *  commentId로 비교하면 타입이 안 맞고,
         *  commentId에서 끌어온 엔티티를 대상으로 where절 비교해야 맞음.
         */
        TestComment comment = em.find(TestComment.class, commentId);

        List<Likes> resultList = em.createQuery("select L from Likes  as L where " +
                "L.testComment = :comment and  L.userName = :userName", Likes.class)
                .setParameter("comment", comment)
                .setParameter("userName", userName)
                .getResultList();

        System.out.println(" 비어있나요 @@@@@"+resultList);
        // 못 찾았으면 null, 있으면 그것을 반환
        if(resultList.isEmpty())
            return null;
        return resultList.get(0);
    }

    public void addLikesRepository(Long commentId, String userName) {

       // 좋아요 1 늘린다.
        TestComment comment = em.find(TestComment.class, commentId);
        CommentData data = comment.getCommentData();
        data.addLikes();

        // 좋아요 테이블에 이름과 번호(FK TestComment 엔티티) 저장
        Likes likes = new Likes(comment, userName);
        em.persist(likes);
    }
}
