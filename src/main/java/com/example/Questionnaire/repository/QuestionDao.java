package com.example.Questionnaire.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Questionnaire.entity.Question;

@Repository
@Qualifier("questionDao")
public interface QuestionDao extends JpaRepository<Question, Long> {

	@Query("SELECT q FROM Question q WHERE q.questionnaire.questionnaireId = :questionnaireId")
	public List<Question> getQuestionsOfQuestionnaire(@Param(value = "questionnaireId")Long questionnaireId);
}
