package com.example.QuizApp.controller;

import com.example.QuizApp.dtos.PracticeSetToCreateDTO;
import com.example.QuizApp.dtos.PracticeSetToUpdateDTO;
import com.example.QuizApp.dtos.QuestionsToGetDTO;
import com.example.QuizApp.mapper.PracticeSetMapper;
import com.example.QuizApp.model.PracticeSet;
import com.example.QuizApp.repository.PracticeSetRepository;
import com.example.QuizApp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("practice-set")
@CrossOrigin("*")
public class PracticeSetController {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    PracticeSetRepository practiceSetRepository;

    @GetMapping("/{id}")
    public Mono<PracticeSet> getPracticeSetById(@PathVariable String id){
        return practiceSetRepository.findById(id);
    }

    @GetMapping()
    public Flux<PracticeSet> getAllPracticeSets(){
        return practiceSetRepository.findAll();
    }

    @PostMapping()
    public Mono<ResponseEntity<PracticeSet>> createPracticeSet(@RequestBody PracticeSetToCreateDTO practiceSet){
       PracticeSet set = PracticeSetMapper.practiceSetToCreateToPracticeSet(practiceSet);
       return practiceSetRepository.save(set).map(ResponseEntity::ok);
    }

    @PutMapping("/{practiceSetID}")
    public Mono<ResponseEntity<PracticeSet>> updatePracticeSet(@RequestBody PracticeSetToUpdateDTO practiceSet,@PathVariable String practiceSetID){
        return practiceSetRepository.findById(practiceSetID).flatMap(x->{
            PracticeSet updatedSet=PracticeSetMapper.practiceSetToUpdateToPracticeSet(x,practiceSet);
            return practiceSetRepository.save(updatedSet);
        }).map(ResponseEntity::ok);
    }

    @PostMapping("/{practiceSetID}")
    public Mono<ResponseEntity<PracticeSet>> addQuestionsToPracticeSet(@PathVariable String practiceSetID, @RequestBody QuestionsToGetDTO[] questions){
        return practiceSetRepository.findById(practiceSetID)
                .flatMap(practiceSet -> {
                    QuestionsToGetDTO[] updatedQuestions = combineTwoArraysIntoOne(practiceSet.getQuestions(), questions);
                    practiceSet.setQuestions(updatedQuestions);
                    return practiceSetRepository.save(practiceSet);
                })
                .map(updatedPracticeSet -> new ResponseEntity<>(updatedPracticeSet, HttpStatus.CREATED))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED));
    }


    private QuestionsToGetDTO[] combineTwoArraysIntoOne(QuestionsToGetDTO[] arr1,QuestionsToGetDTO[] arr2){
        QuestionsToGetDTO[] newQuestions= new QuestionsToGetDTO[arr1.length+arr2.length];

        System.arraycopy(arr1, 0, newQuestions, 0, arr1.length);
        System.arraycopy(arr2, arr1.length, newQuestions, arr1.length,  arr2.length);

        return newQuestions;
    }

}
