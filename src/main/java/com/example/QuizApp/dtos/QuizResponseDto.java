package com.example.QuizApp.dtos;

import com.example.QuizApp.model.Answer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizResponseDto {
    private String id;
    private String title;
    private Answer[] answers;
    private Answer[] correctAnswers;
    private Answer[] selectedAnswers;
    private boolean multipleAnswers;
    private boolean isCorrect;
    private String explanation;
}
