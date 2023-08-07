package com.clipper.instagram.web.dto.user;

import com.clipper.instagram.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserUpdateDto {
    // 필수로 받아야 하는 데이터
//    @NotBlank
//    private String username;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
    // 필수로 받지 않아도 되는 데이터
    private String email;
    private String website;
    private String bio;
    private String phone;
    private String gender;

    // 조금 위험함. 코드 수정이 필요할 예정
    public User toEntity(){
        return User.builder()
//                .username(username)
                .nickname(nickname) // 이름 기재 안했으면 문제!! Validation 체크
                .password(password) // 패스워드 기재 안했으면 문제!! Validation 체크
                .email(email)
                .website(website)
                .bio(bio)
                .phone(phone)
                .gender(gender)
                .build();
    }
}