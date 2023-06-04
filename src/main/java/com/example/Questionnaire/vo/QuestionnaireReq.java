package com.example.Questionnaire.vo;

import com.example.Questionnaire.entity.Questionnaire;

public class QuestionnaireReq extends Questionnaire {
	
	private Long creator;

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}
	
	public QuestionnaireReq() {
	}

	public QuestionnaireReq(Long creator) {
		this.creator = creator;
	}

}
