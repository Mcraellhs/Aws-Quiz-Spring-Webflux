package com.example.QuizApp.controller;

import com.example.QuizApp.dtos.QuestionForSaveDTO;
import com.example.QuizApp.dtos.QuestionsToGetDTO;
import com.example.QuizApp.mapper.QuestionMapper;
import com.example.QuizApp.model.Question;
import com.example.QuizApp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping
    public Flux<QuestionsToGetDTO> getAllQuestions(){
        return questionRepository.findAll().map(QuestionMapper::toQuestionToGetDTO);
    }

    @PostMapping
    public Mono<ResponseEntity<Question>> saveQuestion(@RequestBody QuestionForSaveDTO questionForSaveDTO){
        Question newQuestion = QuestionMapper.dtoForSaveToQuestion(questionForSaveDTO);

        return questionRepository.save(newQuestion).map(x-> new ResponseEntity<>(x, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));
    }

}
