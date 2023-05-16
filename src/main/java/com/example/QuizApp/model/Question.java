package com.example.QuizApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Getter
@Setter
public class Question {

    @Id
    private String id;
    private String title;
    private Answer[] answers;
    private Answer[] correctAnswers;
    private boolean multipleAnswers=false;
    private String explanation;

}
