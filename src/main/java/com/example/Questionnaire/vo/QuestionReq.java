package com.example.Questionnaire.vo;

import java.util.List;

import com.example.Questionnaire.entity.Question;

public class QuestionReq extends Question  {

	private Long questionnaireId;
	
	private List<QuestionReq> questionsReq;

	public Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public List<QuestionReq> getQuestionsReq() {
		return questionsReq;
	}

	public void setQuestionsReq(List<QuestionReq> questionsReq) {
		this.questionsReq = questionsReq;
	}

	public QuestionReq() {
	}
	
	public QuestionReq(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public QuestionReq(Long questionnaireId, List<QuestionReq> questionsReq) {
		this.questionnaireId = questionnaireId;
		this.questionsReq = questionsReq;
	}
	
}
