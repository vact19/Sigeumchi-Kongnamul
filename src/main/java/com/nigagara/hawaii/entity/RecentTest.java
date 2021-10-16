package com.nigagara.hawaii.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
@SequenceGenerator(sequenceName = "recentTest_sequence_generator"
                                    ,name = "recentTest_sequence")
public class RecentTest {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE
                                ,generator = "recentTest_sequence_generator")
    @Column(name = "recentTest_id")
    private Long id;

    // 최근테스트가 多, 지금 대상으로 하는 유저테이블이 일.
    // 최근테스트 테이블의 외래키인 이 컬럼은 유저테이블의 PK를 참조함.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recentTest_userid")
    private User user;

    private String recentTestName;
    private String recentTestUrl;

}
