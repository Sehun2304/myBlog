package com.sparta.myblog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor

@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //기본키를 자동으로 생성해주는 @GeneratedValue를 사용하여 id를 생성해준다
    private Long id;

    // unique = true -> 유일성 조건 설정
    //데이터베이스에 명시적으로 not null 제약 조건을 걸어준다
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;



    // user 객체 생성시 필요한 정보 = username, password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
