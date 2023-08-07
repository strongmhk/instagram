package com.clipper.instagram.web.dto.auth;

import com.clipper.instagram.domain.user.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data // Getter, Setter를 만드는 애노테이션
public class SignupReqDto {
    @Size(min = 2, max = 20)
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String nickname;

    public User toEntity(){
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .nickname(nickname)
                .build();
    }


}
