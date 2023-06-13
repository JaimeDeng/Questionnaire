package com.example.Questionnaire.services;

import com.example.Questionnaire.vo.QuestionnaireReq;
import com.example.Questionnaire.vo.QuestionnaireResp;

public interface QuestionnaireService {
	
	public QuestionnaireResp createQuestionnaire (QuestionnaireReq questionnaireReq);
	public QuestionnaireResp editQuestionnaire (QuestionnaireReq questionnaireReq);
	public QuestionnaireResp getAllQuestionnaire ();
	public QuestionnaireResp getQuestionnairesDataNum ();
	public QuestionnaireResp getQuestionnairesByPage (Integer page);
	public QuestionnaireResp getQuestionnaireByQuestionnaireId (Long questionnaireId);
	public QuestionnaireResp getQuestionnaireNumByKeyword (String keyword);
	public QuestionnaireResp getQuestionnaireByKeyword (String keyword);
	public QuestionnaireResp getQuestionnaireByKeywordAsPage (Integer page , String keyword);
	public QuestionnaireResp getQuestionnairesInCriteria (Integer page ,String keyword, String startTime , String endTime);
	public QuestionnaireResp getQuestionnaireNumInCriteria (String keyword , String startTime , String endTime);
	public QuestionnaireResp deleteQuestionnaire (Long questionnaireId);

}
