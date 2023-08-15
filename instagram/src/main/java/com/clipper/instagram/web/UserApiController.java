package com.clipper.instagram.web;

import com.clipper.instagram.config.auth.PrincipalDetails;
import com.clipper.instagram.domain.user.User;
import com.clipper.instagram.handler.ex.CustomValidationApiException;
import com.clipper.instagram.service.UserService;
import com.clipper.instagram.web.dto.CMRespDto;
import com.clipper.instagram.web.dto.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApiController {

    private final UserService userService;


    @GetMapping("/users")
    public ResponseEntity<?> userList(){
        List<User> userList = userService.userList();
        return new ResponseEntity<>(new CMRespDto<>(1, "성공", userList), HttpStatus.OK);
    }



    @PutMapping("/user/{id}")
    public CMRespDto<?> update(
            @PathVariable int id,
            @Valid @RequestBody UserUpdateDto userUpdateDto,
            BindingResult bindingResult, // 꼭 @Valid가 적혀있는 다음 파라미터에 적어야됨
            @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            throw new CustomValidationApiException("유효성검사 실패함", errorMap);
        } else {
            User userEntity = userService.updateInfo(id, userUpdateDto.toEntity());
            principalDetails.setUser(userEntity); // 세션 정보 변경
            return new CMRespDto<>(1, "회원수정완료", userEntity); // 응답시에 userEntity의 모든 getter 함수가 호출되고 JSON으로 파싱하여 응답한다.
        }

    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id){
        userService.deleteUser(id);
        return new ResponseEntity<>(new CMRespDto<>(1, "회원삭제 성공", null), HttpStatus.OK);
    }




}
