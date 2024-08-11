package com.project.ity.domain.user.dto;

import lombok.Getter;

@Getter
public enum SocialType {
    IPHONE("iphone"),
    GOOGLE("google"),
    KAKAO("kakao");

    private final String ROLE_PREFIX = "ROLE_";
    private String name;

    SocialType(String name) {
        this.name = name;
    }

    public String getRoleType() {
        return ROLE_PREFIX + name.toUpperCase();
    }

    public String getValue() {
        return name;
    }

    public boolean isEquals(String authority) {
        return this.name.equals(authority);
    }
}
