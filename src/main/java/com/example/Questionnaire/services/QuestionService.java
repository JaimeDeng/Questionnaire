package com.example.Questionnaire.services;

import com.example.Questionnaire.vo.QuestionReq;
import com.example.Questionnaire.vo.QuestionResp;

public interface QuestionService {

	public QuestionResp createQuestion(QuestionReq questionReq);
	public QuestionResp editQuestion(QuestionReq questionReq);
	public QuestionResp getAllQuestion();
	public QuestionResp getQuestionsOfQuestionnaire(Long questionnaireId);
	
}
