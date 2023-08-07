package com.example.QuizApp.model;

import com.example.QuizApp.dtos.QuestionsToGetDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class PracticeSet {
    @Id
    private String id;
    private String title;
    private QuestionsToGetDTO[] questions;
    private Difficulty difficulty;
}
