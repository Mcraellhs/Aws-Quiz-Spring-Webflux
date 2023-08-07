package com.example.QuizApp.dtos;

import com.example.QuizApp.model.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PracticeSetToCreateDTO {
    private String title;
    private QuestionsToGetDTO[] questions;
    private Difficulty difficulty;
}
