package com.example.QuizApp.dtos;

import com.example.QuizApp.model.Answer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionsToGetDTO {
    private String id;
    private String title;
    private Answer[] answers;
    private boolean multipleAnswers=false;
}
