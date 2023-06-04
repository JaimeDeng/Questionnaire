package com.example.Questionnaire.vo;

import java.util.List;

import com.example.Questionnaire.entity.Answer;

public class AnswerReq extends Answer {
	
	private Long questionnaireId;
	
	private Long questionId;
	
	private Long respondent;
	
	private List<AnswerReq> answersReq;
	
	
	

	public Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getRespondent() {
		return respondent;
	}

	public void setRespondent(Long respondent) {
		this.respondent = respondent;
	}
	
	public List<AnswerReq> getAnswersReq() {
		return answersReq;
	}

	public void setAnswersReq(List<AnswerReq> answersReq) {
		this.answersReq = answersReq;
	}

	public AnswerReq() {
	}

	public AnswerReq(Long questionnaireId, Long questionId, Long respondent) {
		this.questionnaireId = questionnaireId;
		this.questionId = questionId;
		this.respondent = respondent;
	}

	public AnswerReq(Long questionnaireId, Long questionId, Long respondent, List<AnswerReq> answersReq) {
		this.questionnaireId = questionnaireId;
		this.questionId = questionId;
		this.respondent = respondent;
		this.answersReq = answersReq;
	}
	

}
