package com.example.QuizApp.dtos;

import com.example.QuizApp.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@AllArgsConstructor
@Getter
public class QuestionForSaveDTO {
    private String title;
    private Answer[] answers;
    private Answer[] correctAnswers;
    private String explanation;
}
