package com.userj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.userj.domain.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
