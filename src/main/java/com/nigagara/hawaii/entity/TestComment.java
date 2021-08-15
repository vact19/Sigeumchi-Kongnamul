package com.nigagara.hawaii.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SequenceGenerator(name = "comment-seq-generator", sequenceName = "comment_seq")
@Entity
@Setter @Getter
public class TestComment {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment-seq-generator")
    @Column(name = "comment_id")
    private Long id;

    @Embedded
    private CommentData commentData; // Embedded Value Type으로 재사용 혹은 특정 기능 구현

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id") // TestComment->Test 다대일 매핑
    private TestEntity testEntity;
    /**
     * 이후 TestComment.setTest(test.class)
     *  특정 테스트에 소속시킴
     */
}
