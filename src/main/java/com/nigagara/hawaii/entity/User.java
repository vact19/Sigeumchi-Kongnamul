package com.nigagara.hawaii.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter @Getter
public class User {

    @Id @GeneratedValue
    @Column(name = "user_identifier")
    private Long id;

    @Column(name = "username") // 식별자 identifier.와 실제 유저이름 userName
    private String userName;

    private String password;

    private String Email;

    private String pwdQuestion;
    private String pwdAnswer;
    private String pwdHint;      // 비밀번호 찾기 시 질문, 답 입력 시 힌트 표시.
    // 비밀번호 변경도 구현해보자
    // 최종적으로는 이메일로 새 임시 비밀번호 발급까지 구현 목표
    private LocalDateTime regDate; // 이후 LocalDateTime.now()

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", Email='" + Email + '\'' +
                ", pwdQuestion='" + pwdQuestion + '\'' +
                ", pwdAnswer='" + pwdAnswer + '\'' +
                ", pwdHint='" + pwdHint + '\'' +
                ", regDate=" + regDate +
                '}';
    }
    public boolean isThisEmpty(){
        if(this==null)
            return true;
        return false;
    }
}
