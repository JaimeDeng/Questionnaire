package com.example.Questionnaire.services;

import com.example.Questionnaire.vo.AnswerReq;
import com.example.Questionnaire.vo.AnswerResp;

public interface AnswerService {
	
	public AnswerResp createAnswer(AnswerReq answerReq);
	public AnswerResp getAllAnswer();
	public AnswerResp getAnswersOfQuestion(Long questionId);
	public AnswerResp getSomeonesAnswersOfQuestionnaire(Long userId , Long questionnaireId);

}
