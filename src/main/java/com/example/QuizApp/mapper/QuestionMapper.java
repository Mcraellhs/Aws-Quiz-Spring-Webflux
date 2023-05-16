package com.example.QuizApp.mapper;

import com.example.QuizApp.dtos.QuestionForSaveDTO;
import com.example.QuizApp.dtos.QuestionsToGetDTO;
import com.example.QuizApp.model.Answer;
import com.example.QuizApp.model.Question;

import java.util.Arrays;

public class QuestionMapper {

    public static Question dtoForSaveToQuestion(QuestionForSaveDTO questionForSaveDTO){
        Question newQuestion = new Question();
        if (questionForSaveDTO.getCorrectAnswers().length>1){
            newQuestion.setMultipleAnswers(true);
        }
         setIdToAnswersAndCorrectAnswers(questionForSaveDTO);

        newQuestion.setTitle(questionForSaveDTO.getTitle());
        newQuestion.setAnswers(questionForSaveDTO.getAnswers());
        newQuestion.setCorrectAnswers(questionForSaveDTO.getCorrectAnswers());
        newQuestion.setExplanation(questionForSaveDTO.getExplanation());

        return newQuestion;
    }

    public static QuestionsToGetDTO toQuestionToGetDTO(Question question){
        QuestionsToGetDTO questionsToGetDTO = new QuestionsToGetDTO();

        questionsToGetDTO.setId(question.getId());
        questionsToGetDTO.setTitle(question.getTitle());
        questionsToGetDTO.setAnswers(question.getAnswers());
        questionsToGetDTO.setMultipleAnswers(question.isMultipleAnswers());

        return questionsToGetDTO;
    }


    private static void setIdToAnswersAndCorrectAnswers(QuestionForSaveDTO questionForSaveDTO){
        Answer[] answers = questionForSaveDTO.getAnswers();
        Answer[] correctAnswers = questionForSaveDTO.getCorrectAnswers();
        for (int i = 0;i<answers.length;i++){
            answers[i].setId(i);
            for (Answer x: correctAnswers) {
                if (x.getTitle().equals(answers[i].getTitle())){
                    x.setId(i);
                }
            }
        }
    }

}
