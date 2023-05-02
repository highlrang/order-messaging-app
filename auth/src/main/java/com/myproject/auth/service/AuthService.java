package com.myproject.auth.service;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.myproject.auth.dto.LoginDto;
import com.myproject.auth.dto.UserResponseDto;
import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.user.domain.MemberEntity;
import com.myproject.core.user.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService{

    private final HttpSession httpSession;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorCode.DATA_NOT_FOUND));
        return new UserResponseDto(memberEntity);
    }

    private void matchPassword(String rawPassword, String encodedPassword){
        if(!passwordEncoder.matches(rawPassword, encodedPassword))
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
    }

    private void setSession(UserDetails userDetails){
        httpSession.setAttribute("user", (UserResponseDto) userDetails);
    }

    public UserResponseDto login(LoginDto loginDto) {
        UserDetails userDetails = loadUserByUsername(loginDto.getEmail());
        matchPassword(loginDto.getPassword(), userDetails.getPassword());
        setSession(userDetails);
        return (UserResponseDto) userDetails;
    }
    
}
