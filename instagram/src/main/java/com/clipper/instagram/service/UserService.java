package com.clipper.instagram.service;

import com.clipper.instagram.domain.user.User;
import com.clipper.instagram.domain.user.UserRepository;
import com.clipper.instagram.handler.ex.CustomValidationApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> userList(){
        return userRepository.findAll();
    }



    @Transactional
    public User updateInfo(int id, User user){
        //1.영속화
        User userEntity = userRepository.findById(id).orElseThrow(() -> { throw new CustomValidationApiException("찾을 수 없는 id입니다.")
                ;}); // 1. 무조건 찾았다. 걱정마 get() 2. 못찾았어 익셉션 발동시킬게 orElseThrow()

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        //2.영속화된 오브젝트를 수정 - 더티체킹 (업데이트 완료)
        userEntity.setNickname(user.getNickname());
        userEntity.setPassword(encPassword);
        userEntity.setEmail(user.getEmail());
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    } // 더티체킹이 일어나서 업데이트가 완료됨(영속성 컨텍스트가 자동으로 해줌), userRepository.save 안해도됨


}
