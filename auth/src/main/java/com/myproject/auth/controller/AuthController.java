package com.myproject.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.auth.dto.LoginDto;
import com.myproject.auth.dto.UserResponseDto;
import com.myproject.auth.service.AuthService;
import com.myproject.core.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginDto loginDto){
        UserResponseDto userDto = authService.login(loginDto);
        return ResponseEntity.ok(ApiResponse.success(userDto));
    }
}
