package com.example.Questionnaire.vo;

import com.example.Questionnaire.entity.Questionnaire;

public class QuestionnaireReq extends Questionnaire {
	
	private Long creator;
	
	private String startTimeStr;
	
	private String endTimeStr;

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public String getStartTimeStr() {
		return startTimeStr;
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startTimeStr = startTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public QuestionnaireReq() {
	}

	public QuestionnaireReq(Long creator) {
		this.creator = creator;
	}

	public QuestionnaireReq(Long creator, String startTimeStr, String endTimeStr) {
		this.creator = creator;
		this.startTimeStr = startTimeStr;
		this.endTimeStr = endTimeStr;
	}

}
