package com.myproject.auth.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.myproject.core.common.enums.ErrorCode;
import com.myproject.core.common.exception.CustomException;
import com.myproject.core.common.utils.HeaderUtil;
import com.myproject.core.user.dto.UserResponseDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        UserResponseDto userDto = HeaderUtil.getAuth(request);
        
        if(userDto != null){
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDto, userDto.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
    }
    
}
