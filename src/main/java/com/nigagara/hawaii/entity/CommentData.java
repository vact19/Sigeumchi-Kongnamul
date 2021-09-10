package com.nigagara.hawaii.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Setter @Getter
public class CommentData {

    private String userName; // 단순 이름 저장용. User 테이블 매핑 필요 없음
    private String content;
    private Long likes;

    public CommentData(String userName, String content, Long likes) {
        this.userName = userName;
        this.content = content;
        this.likes = likes;
    }
    public void addLikes(){
        likes++;
    }

    protected CommentData() { } // 'CommentData' should have [public, protected] no-arg constructor
}
