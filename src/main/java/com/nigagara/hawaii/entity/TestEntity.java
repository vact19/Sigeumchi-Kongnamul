package com.nigagara.hawaii.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Setter @Getter
public class TestEntity {

    @Id @GeneratedValue // 식별자
    @Column(name = "test_id")
    private Long id; // Long -> Not Null


    private String testName;
}