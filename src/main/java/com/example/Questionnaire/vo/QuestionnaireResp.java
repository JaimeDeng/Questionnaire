package com.example.Questionnaire.vo;

import java.util.List;

import com.example.Questionnaire.entity.Questionnaire;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QuestionnaireResp extends Questionnaire {

	public String code;
	
	public String message;
	
	public boolean success;
	
	private List<Questionnaire> questionnaires;

	
	
	public List<Questionnaire> getQuestionnaires() {
		return questionnaires;
	}

	public void setQuestionnaires(List<Questionnaire> questionnaires) {
		this.questionnaires = questionnaires;
	}
	
	public QuestionnaireResp() {
	}
	
	public QuestionnaireResp( List<Questionnaire> questionnaires, String code, String message, boolean success) {
		this.questionnaires = questionnaires;
		this.code = code;
		this.message = message;
		this.success = success;
	}

	public QuestionnaireResp(String code, String message, boolean success) {
		this.code = code;
		this.message = message;
		this.success = success;
	}
	
}
