package com.clipper.instagram.web;

import com.clipper.instagram.config.auth.PrincipalDetails;
import com.clipper.instagram.domain.user.User;
import com.clipper.instagram.handler.ex.CustomValidationException;
import com.clipper.instagram.service.AuthService;
import com.clipper.instagram.web.dto.CMRespDto;
import com.clipper.instagram.web.dto.auth.SignupReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping ("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupReqDto signupReqDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError error : bindingResult.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            throw new CustomValidationException("유효성검사 실패함", errorMap);
        }else {
            // User라는 객체에 SignupDto에 있는 데이터를 넣기
            User user = signupReqDto.toEntity();
            User userEntity = authService.signup(user);
            return new ResponseEntity<>(new CMRespDto<>(1, "회원가입 성공",  userEntity), HttpStatus.CREATED);
        }

    }


    @GetMapping("/test")
    public String test(Authentication authentication){
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("authentication = " + principal.toString());
        return "인증된 사용자 입니다.";
    }




}
