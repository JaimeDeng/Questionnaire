package com.example.Questionnaire.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Questionnaire.entity.Answer;
import com.example.Questionnaire.entity.Question;

@Repository
@Qualifier("answerDao")
public interface AnswerDao extends JpaRepository<Answer , Long> {

	@Query("SELECT a FROM Answer a WHERE a.question.questionId = :questionId")
	public List<Answer> getAnswersOfQuestion(@Param(value = "questionId")Long questionId);
	
	@Query("SELECT a FROM Answer a WHERE a.questionnaire.questionnaireId = :questionnaireId AND "
			+ "a.user.userId = :userId")
	public List<Answer> getSomeonesAnswersOfQuestionnaire(@Param(value = "userId")
	Long userId , @Param(value = "questionnaireId")Long questionnaireId);
	
}
