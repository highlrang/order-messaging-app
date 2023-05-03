package com.myproject.auth.service;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
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
import com.myproject.core.common.utils.EncryptionUtil;
import com.myproject.core.user.domain.MemberEntity;
import com.myproject.core.user.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService{

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("header.secretKey")
    private String secretKey;

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

    private String encrypted(UserDetails userDetails) throws Exception{
        UserResponseDto userDto = (UserResponseDto) userDetails;
        return EncryptionUtil.encrypt(secretKey, userDto.toString());
    }

    public String login(LoginDto loginDto) {
        try{
            UserDetails userDetails = loadUserByUsername(loginDto.getEmail());
            // matchPassword(loginDto.getPassword(), userDetails.getPassword());
            return encrypted(userDetails);

        }catch(Exception e){
            log.error(e.toString() + "\n" + Arrays.toString(e.getStackTrace()));
            throw new CustomException(ErrorCode.AUTHENTICATION_FAILED);
        }
    }
    
}
