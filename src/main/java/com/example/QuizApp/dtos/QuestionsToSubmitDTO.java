package com.example.QuizApp.dtos;

import com.example.QuizApp.model.Answer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionsToSubmitDTO {
    private String id;
    private String title;
    private Answer[] selectedAnswers;

}
