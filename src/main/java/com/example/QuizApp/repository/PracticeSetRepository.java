package com.example.QuizApp.repository;

import com.example.QuizApp.model.PracticeSet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PracticeSetRepository extends ReactiveMongoRepository<PracticeSet, String> {
}
