package com.myproject.core.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleType {
    
    MEMBER("MEMBER", "회원"),
    STORE("STORE", "가게"),
    RIDER("RIDER", "배달원"),
    ADMIN("ADMIN", "관리자"),
    ;

    private final String code;
    private final String displayName;
}
