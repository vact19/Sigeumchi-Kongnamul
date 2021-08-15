package com.nigagara.hawaii.service;

import com.nigagara.hawaii.entity.TestComment;
import com.nigagara.hawaii.entity.TestEntity;
import com.nigagara.hawaii.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

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

}












