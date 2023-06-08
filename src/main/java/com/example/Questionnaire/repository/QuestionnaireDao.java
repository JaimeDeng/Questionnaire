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
	
	@Query(value= "SELECT COUNT(*) FROM Questionnaire q WHERE q.title LIKE %:keyword%", nativeQuery = true)
	public Long getQuestionnaireNumByKeyword(@Param(value = "keyword")String keyword);

	@Query("SELECT q FROM Questionnaire q WHERE q.title LIKE %:keyword%")
	public List<Questionnaire> getQuestionnaireByKeyword(@Param(value = "keyword")String keyword);
	
	@Query(value="SELECT * FROM Questionnaire q WHERE q.title LIKE %:keyword% LIMIT :page, 5", nativeQuery = true )
	public List<Questionnaire> getQuestionnaireByKeywordAsPage(@Param(value = "page")Integer page , 
			@Param(value = "keyword")String keyword);
	
	@Query(value="SELECT * FROM Questionnaire q WHERE (q.start_time >= :startTime OR :startTime IS NULL) AND "
			+ "(q.end_time <= :endTime OR :endTime IS NULL) LIMIT :page, 5", nativeQuery = true )
	public List<Questionnaire> getQuestionnairesInTimeFrame(@Param(value = "page")Integer page , 
			@Param(value = "startTime")String startTime , @Param(value = "endTime")String endTime);
	
	@Query(value="SELECT COUNT(*) FROM Questionnaire q WHERE (q.start_time >= :startTime OR :startTime IS NULL) AND "
			+ "(q.end_time <= :endTime OR :endTime IS NULL)", nativeQuery = true )
	public Long getQuestionnaireNumInTimeFrame(@Param(value = "startTime")String startTime , 
			@Param(value = "endTime")String endTime);
	
	//LIMIT 筆數index起始(page - 1 x 筆數) , 筆數
	@Query(value="SELECT * FROM Questionnaire q LIMIT :page, 5", nativeQuery = true)
	public List<Questionnaire> getQuestionnairesByPage(@Param(value = "page")Integer page);
	
}