package com.clipper.instagram.config;

import com.clipper.instagram.config.jwt.JwtAuthenticationFilter;
import com.clipper.instagram.config.jwt.JwtAuthorizationFilter;
import com.clipper.instagram.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않는 stateless 서버를 만들겠다.
                .and() // 서버마다 세션영역이 따로있어서, 서버를 여러 개 두는 경우에는 인증방식을 세션보다는 jwt를 이용하는게 좋다
                .addFilter(corsFilter) // @CrossOrigin(인증 X), 시큐리티 필터에 등록 인증(O)
                .formLogin().disable() // form 태그를 이용해 로그인 하지 않겠다.
                .httpBasic().disable() // http Basic(header에 Authorization : ID,PW 를 담아감 근데 암호화가 안돼서 보안에 취약) , http Bearer(header에 Authorization : token을 담아감 ID, PW 직접적 노출이 없어 보안 ㄱㅊ)
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) // AuthenticationManager를 던져줘야함(WebSecurityConfigurerAdapter에 존재함)
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))


                // 로그인, 회원가입 API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll 설정
                .authorizeRequests()
                /*.antMatchers("/api/signup/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/api/subscribe/**").permitAll()*/

                // 나머지 API는 모두 인증 필요
                .anyRequest().authenticated();


    }




}
