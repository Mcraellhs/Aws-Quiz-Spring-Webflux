package com.example.QuizApp.repository;

import com.example.QuizApp.model.Question;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface QuestionRepository extends ReactiveMongoRepository<Question,String> {
}
