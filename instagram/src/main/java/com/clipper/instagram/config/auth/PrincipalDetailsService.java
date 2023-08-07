package com.clipper.instagram.config.auth;

import com.clipper.instagram.domain.user.User;
import com.clipper.instagram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

// http://localhost:8080/login => formLogin().disable()로 해놔서 동작을 안함
// 스프링 시큐리티의 로그인 요청 기본 경로가 /login
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // 로그인시 실행되는 메서드
    // password는 알아서 체킹, username만 잘되면 된다
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if(userEntity == null){
            throw new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다 : " + username);
        }else{
            return new PrincipalDetails(userEntity);
        }

    }
}
