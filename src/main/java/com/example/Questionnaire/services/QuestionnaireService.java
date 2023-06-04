package com.example.Questionnaire.services;

import com.example.Questionnaire.vo.QuestionnaireReq;
import com.example.Questionnaire.vo.QuestionnaireResp;

public interface QuestionnaireService {
	
	public QuestionnaireResp createQuestionnaire (QuestionnaireReq questionnaireReq);
	public QuestionnaireResp editQuestionnaire (QuestionnaireReq questionnaireReq);
	public QuestionnaireResp getAllQuestionnaire ();
	public QuestionnaireResp getQuestionnaireByQuestionnaireId (Long questionnaireId);
	public QuestionnaireResp getQuestionnaireByKeyword (String keyword);
	public QuestionnaireResp deleteQuestionnaire (Long questionnaireId);

}
