package com.nigagara.hawaii.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
public class Likes {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "likes_id") // name을 따로 정하고 싶을 때 @Column
    private Long id;

    /**
     *  굳이 FK로 사용하지 않고 CommnetID를 클라이언트에서 받아와서
     *  바로 넣어도 되지만, 확장성과 학습을 위해 다대일 연관관계 설정
     */
    @ManyToOne(fetch = FetchType.LAZY) // 좋아요가 多, 코멘트가 일
    @JoinColumn(name = "likes_test_id")
    private TestComment testComment;

    private String userName;

    public Likes(TestComment testComment, String userName) {
        this.testComment = testComment;
        this.userName = userName;
    }
    public Likes(){}
}
