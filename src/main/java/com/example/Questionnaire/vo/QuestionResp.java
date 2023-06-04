package com.example.Questionnaire.vo;

import java.util.List;

import com.example.Questionnaire.entity.Question;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuestionResp extends Question {

	public String code;
	
	public String message;
	
	public boolean success;
	
	private List<Question> questions;

	
	
	
	public String getCode() {
		return code;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public QuestionResp() {
	}
	
	public QuestionResp(List<Question> questions, String code , String message, boolean success) {
		this.questions = questions;
		this.code = code;
		this.message = message;
		this.success = success;
	}

	public QuestionResp(String code, String message, boolean success) {
		this.code = code;
		this.message = message;
		this.success = success;
	}
	
}
