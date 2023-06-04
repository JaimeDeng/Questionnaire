package com.example.Questionnaire.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Questionnaire.entity.Question;
import com.example.Questionnaire.entity.Questionnaire;

@Repository
@Qualifier("questionnaireDao")
public interface QuestionnaireDao extends JpaRepository<Questionnaire , Long> {

	@Query("SELECT q FROM Questionnaire q WHERE q.title LIKE %:keyword%")
	public List<Questionnaire> getQuestionnaireByKeyword(@Param(value = "keyword")String keyword);
}
