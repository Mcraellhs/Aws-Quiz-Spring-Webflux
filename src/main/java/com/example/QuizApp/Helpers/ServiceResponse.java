package com.example.QuizApp.Helpers;

import com.example.QuizApp.security.ApplicationUserRole;
import lombok.Getter;

@Getter
public class ServiceResponse<T> {

    private final T data;
    private final ApplicationUserRole role;

    public ServiceResponse(T data, ApplicationUserRole role) {
        this.data = data;
        this.role = role;
    }



}