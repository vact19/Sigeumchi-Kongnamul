package com.nigagara.hawaii.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Setter @Getter
@SequenceGenerator(name = "member-seq-generator", sequenceName = "member_seq"
        )
public class TestEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member-seq-generator") // 식별자
    @Column(name = "test_id")
    private Long id; // Long -> Not Null

    private String testName;
    private String testType;
    //조회수
    private int view;
}