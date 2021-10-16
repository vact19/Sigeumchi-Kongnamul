package com.nigagara.hawaii.service;


import com.nigagara.hawaii.entity.Likes;
import com.nigagara.hawaii.entity.TestComment;
import com.nigagara.hawaii.entity.TestEntity;
import com.nigagara.hawaii.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public Long saveComment(TestComment comment){
        commentRepository.save(comment);
        Long id = comment.getTestEntity().getId();
        return id; //
    }

    public List<TestComment> findComments(TestEntity testEntity){
        return commentRepository.findAll(testEntity);
    }

    @Transactional
    public Long removeComment(Long id){
        Long idToReturn = commentRepository.remove(id);
        return idToReturn;
    }

    @Transactional
    public boolean likes_isSameUser(Long commentId, String userName) {

        // get(0) 에서 NPE 발생 가능성 있으므로 예외처리
        /**
         *  NPE가 아니라 .IndexOutOfBoundsException 발생
         */
        Likes likesResult = commentRepository.likes_isSame(commentId, userName);
        Likes likes = Optional.ofNullable(likesResult).orElse(new Likes());
        log.info("getID는 @@@@@@@{}", likes.getId());


        if(likes.getId()==null){ // 중복 아님
            return false;
        } else {  // 댓글Id랑 이름으로 조회해서 결과가 나왔으므로 중복임
            return true;
        }

    }

    @Transactional
    public void addLikes(Long commentId, String userName) {
        commentRepository.addLikesRepository(commentId, userName);
    }
}












