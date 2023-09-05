package com.example.QuizApp.security;

public enum ApplicationUserPermission {

    USER_READ("user:read"),
    USER_WRITE("user:write"),
    CONTENT_WRITE("content:write"),
    CONTENT_READ("content:read");




    private final String permission;

    ApplicationUserPermission(String permission) {
        this.permission = permission;
    }
    public String getPermission() {
        return permission;
    }
}