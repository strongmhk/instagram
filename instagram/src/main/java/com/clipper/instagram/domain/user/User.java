package com.clipper.instagram.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // 디비에 테이블을 생성
public class User {
    @Id // primary key로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다.
    private int id;

    @Column(length = 20, unique = true)
    private String username; // 회원가입할 때 입력받을 회원 id
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String nickname;
    private String website; // 웹 사이트
    private String bio; // 자기 소개
    @Column(nullable = false)
    private String email;
    private String phone;
    private String gender;

    private String profileImageUrl; // 사진
    private String role; // 권한


    public List<String> getRoleList(){
        if(this.role.length() > 0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();

    }

}
