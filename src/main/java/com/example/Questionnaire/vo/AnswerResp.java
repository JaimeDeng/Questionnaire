package com.example.Questionnaire.vo;

import java.util.List;

import com.example.Questionnaire.entity.Answer;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AnswerResp extends Answer {

	public String code;
	
	public String message;
	
	public boolean success;
	
	private List<Answer> answers;

	
	
	
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public AnswerResp(String code, String message, boolean success) {
		this.code = code;
		this.message = message;
		this.success = success;
	}

	public AnswerResp(List<Answer> answers , String code, String message, boolean success) {
		this.answers = answers;
		this.code = code;
		this.message = message;
		this.success = success;
	}
	
}
