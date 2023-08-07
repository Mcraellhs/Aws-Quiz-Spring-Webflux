package com.example.QuizApp.mapper;

import com.example.QuizApp.dtos.PracticeSetToCreateDTO;
import com.example.QuizApp.dtos.PracticeSetToUpdateDTO;
import com.example.QuizApp.model.PracticeSet;

public class PracticeSetMapper {

    public static PracticeSet practiceSetToCreateToPracticeSet(PracticeSetToCreateDTO practiceSetToCreateDTO){

        PracticeSet practiceSet = new PracticeSet();
        practiceSet.setQuestions(practiceSetToCreateDTO.getQuestions());
        practiceSet.setTitle(practiceSetToCreateDTO.getTitle());
        practiceSet.setDifficulty(practiceSetToCreateDTO.getDifficulty());

        return practiceSet;
    }

    public static PracticeSet practiceSetToUpdateToPracticeSet(PracticeSet practiceSet,PracticeSetToUpdateDTO practiceSetToUpdateDTO){
        practiceSet.setDifficulty(practiceSetToUpdateDTO.getDifficulty());
        practiceSet.setTitle(practiceSetToUpdateDTO.getTitle());
        practiceSet.setQuestions(practiceSetToUpdateDTO.getQuestions());

        return practiceSet;
    }

}
