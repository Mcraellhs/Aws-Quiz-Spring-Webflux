package com.example.QuizApp.controller;

import com.example.QuizApp.dtos.QuestionsToSubmitDTO;
import com.example.QuizApp.dtos.QuizResponseDto;
import com.example.QuizApp.model.Answer;
import com.example.QuizApp.model.Question;
import com.example.QuizApp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    QuestionRepository questionRepository;

    @PostMapping
    public Flux<QuizResponseDto> submitQuiz(@RequestBody QuestionsToSubmitDTO[] questionsToSubmitDTOS) {
        return Flux.fromArray(questionsToSubmitDTOS)
                .flatMap(this::processQuestion);
    }

    private Mono<QuizResponseDto> processQuestion(QuestionsToSubmitDTO questionDTO) {
        return questionRepository.findById(questionDTO.getId())
                .map(question -> createQuizResponse(question, questionDTO.getSelectedAnswers()));
    }
    private boolean isAnswerCorrect(Answer correctAnswer,Answer[] selectedAnswers){
        if (selectedAnswers.length == 0) {
            return false;
        }
        for (Answer selectedAnswer : selectedAnswers) {
            if (correctAnswer.getId() != selectedAnswer.getId()) {
                return false;
            }
        }
     return true;
    }

    private QuizResponseDto createQuizResponse(Question question, Answer[] selectedAnswers) {
        QuizResponseDto quiz = new QuizResponseDto();

        Answer[] correctAnswers = question.getCorrectAnswers();
        for (Answer correctAnswer : correctAnswers) {
            if (!isAnswerCorrect(correctAnswer, selectedAnswers)) {
                quiz.setCorrect(false);
                break;
            } else {
                quiz.setCorrect(true);
            }
        }

        quiz.setCorrectAnswers(question.getCorrectAnswers());
        quiz.setId(question.getId());
        quiz.setExplanation(question.getExplanation());
        quiz.setMultipleAnswers(question.isMultipleAnswers());
        quiz.setSelectedAnswers(selectedAnswers);
        quiz.setTitle(question.getTitle());
        quiz.setAnswers(question.getAnswers());

        return quiz;
    }

}
