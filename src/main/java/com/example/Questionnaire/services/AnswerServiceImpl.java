package com.example.Questionnaire.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.Questionnaire.constants.RtnCode;
import com.example.Questionnaire.entity.Answer;
import com.example.Questionnaire.entity.Question;
import com.example.Questionnaire.entity.Questionnaire;
import com.example.Questionnaire.entity.User;
import com.example.Questionnaire.repository.AnswerDao;
import com.example.Questionnaire.repository.QuestionDao;
import com.example.Questionnaire.repository.QuestionnaireDao;
import com.example.Questionnaire.repository.UserDao;
import com.example.Questionnaire.vo.AnswerReq;
import com.example.Questionnaire.vo.AnswerResp;

@Service
@Qualifier("answerService")
public class AnswerServiceImpl implements AnswerService {
	
	private QuestionnaireDao questionnaireDao;
	private UserDao userDao;
	private QuestionDao questionDao;
	private AnswerDao answerDao;
	
	@Autowired
	public AnswerServiceImpl (@Qualifier("questionnaireDao")QuestionnaireDao questionnaireDao , 
			@Qualifier("userDao")UserDao userDao ,@Qualifier("questionDao")QuestionDao questionDao , 
			@Qualifier("answerDao")AnswerDao answerDao) {
		this.questionnaireDao = questionnaireDao;
		this.userDao = userDao;
		this.questionDao = questionDao;
		this.answerDao = answerDao;
	}

	@Override
	@Transactional
	public AnswerResp createAnswer(AnswerReq answerReq) {
		List<AnswerReq>answersReq = answerReq.getAnswersReq();
		if(answersReq.size() == 0) {
			//單個寫入
			if(answerReq.getQuestionnaireId() < 1) {
				return new AnswerResp(RtnCode.QUESTIONNAIREID_ILLEGAL.getCode(), 
						RtnCode.QUESTIONNAIREID_ILLEGAL.getMessage(), false);
			}
			Optional<Questionnaire> questionnaire = questionnaireDao.findById(answerReq.getQuestionnaireId());
			if(questionnaire.isEmpty()) {
				return new AnswerResp(RtnCode.QUESTIONNAIRE_NOT_EXISTS.getCode(), 
						RtnCode.QUESTIONNAIRE_NOT_EXISTS.getMessage(), false);
			}
			if(answerReq.getQuestionId() < 1) {
				return new AnswerResp(RtnCode.QUESTIONID_ILLEGAL.getCode(), 
						RtnCode.QUESTIONID_ILLEGAL.getMessage(), false);
			}
			Optional<Question> question = questionDao.findById(answerReq.getQuestionId());
			if(question.isEmpty()) {
				return new AnswerResp(RtnCode.QUESTION_NOT_EXISTS.getCode(), 
						RtnCode.QUESTION_NOT_EXISTS.getMessage(), false);
			}
			if(answerReq.getRespondent() < 1) {
				return new AnswerResp(RtnCode.USERID_ILLEGAL.getCode(), 
						RtnCode.USERID_ILLEGAL.getMessage(), false);
			}
			Optional<User> respondent = userDao.findById(answerReq.getRespondent());
			if(respondent.isEmpty()) {
				return new AnswerResp(RtnCode.USER_NOT_EXISTS.getCode(), 
						RtnCode.USER_NOT_EXISTS.getMessage(), false);
			}
			//是必填
			if(question.get().isRequired() == true) {
				//只要沒有內容都報錯
				if(!StringUtils.hasText(answerReq.getContent())) {
					return new AnswerResp(RtnCode.ANSWER_CANNOT_EMPTY.getCode(), 
							RtnCode.ANSWER_CANNOT_EMPTY.getMessage(), false);
				}
				//是複選題(需要用陣列處理)
				if(question.get().getType() == "select" && question.get().isMultipleChoice() == true) {
					String content = answerReq.getContent();
					String[] contentArr = content.split(",");
					//超過選擇上限報錯
					if(contentArr.length > question.get().getSelectLimit()) {
						return new AnswerResp(RtnCode.EXCEED_SELECT_LIMIT.getCode(), 
								RtnCode.EXCEED_SELECT_LIMIT.getMessage(), false);
					}
				}
			}else {
				//非必填
				//若有填答且是複選題就要檢查是否超過選擇上限
				if(StringUtils.hasText(answerReq.getContent()) && question.get().getType() == "select" 
						&& question.get().isMultipleChoice() == true) {
					String content = answerReq.getContent();
					String[] contentArr = content.split(",");
					//超過選擇上限報錯
					if(contentArr.length > question.get().getSelectLimit()) {
						return new AnswerResp(RtnCode.EXCEED_SELECT_LIMIT.getCode(), 
								RtnCode.EXCEED_SELECT_LIMIT.getMessage(), false);
					}
				}
			}
			Answer answer = new Answer(questionnaire.get(), question.get(), respondent.get(),
					answerReq.getContent());
			answerDao.save(answer);
			return new AnswerResp(RtnCode.ADD_SUCCESSFUL.getCode(), 
					RtnCode.ADD_SUCCESSFUL.getMessage(), true);
		}else {
			//多個寫入
			List<Answer> answers = new ArrayList<>();
			for(AnswerReq thisAnswerReq : answersReq) {
				if(thisAnswerReq.getQuestionnaireId() < 1) {
					return new AnswerResp(RtnCode.QUESTIONNAIREID_ILLEGAL.getCode(), 
							RtnCode.QUESTIONNAIREID_ILLEGAL.getMessage(), false);
				}
				Optional<Questionnaire> questionnaire = questionnaireDao.findById(thisAnswerReq.getQuestionnaireId());
				if(questionnaire.isEmpty()) {
					return new AnswerResp(RtnCode.QUESTIONNAIRE_NOT_EXISTS.getCode(), 
							RtnCode.QUESTIONNAIRE_NOT_EXISTS.getMessage(), false);
				}
				if(thisAnswerReq.getQuestionId() < 1) {
					return new AnswerResp(RtnCode.QUESTIONID_ILLEGAL.getCode(), 
							RtnCode.QUESTIONID_ILLEGAL.getMessage(), false);
				}
				Optional<Question> question = questionDao.findById(thisAnswerReq.getQuestionId());
				if(question.isEmpty()) {
					return new AnswerResp(RtnCode.QUESTION_NOT_EXISTS.getCode(), 
							RtnCode.QUESTION_NOT_EXISTS.getMessage(), false);
				}
				if(thisAnswerReq.getRespondent() < 1) {
					return new AnswerResp(RtnCode.USERID_ILLEGAL.getCode(), 
							RtnCode.USERID_ILLEGAL.getMessage(), false);
				}
				Optional<User> respondent = userDao.findById(thisAnswerReq.getRespondent());
				if(respondent.isEmpty()) {
					return new AnswerResp(RtnCode.USER_NOT_EXISTS.getCode(), 
							RtnCode.USER_NOT_EXISTS.getMessage(), false);
				}
				//是必填
				if(question.get().isRequired() == true) {
					System.out.println("是必填");
					//只要沒有內容都報錯
					if(!StringUtils.hasText(thisAnswerReq.getContent())) {
						return new AnswerResp(RtnCode.ANSWER_CANNOT_EMPTY.getCode(), 
								RtnCode.ANSWER_CANNOT_EMPTY.getMessage(), false);
					}
					//是複選題(需要用陣列處理)
					if(question.get().getType().equals("select") && question.get().isMultipleChoice() == true) {
						System.out.println("是複選");
						String content = thisAnswerReq.getContent();
						String[] contentArr = content.split(",");
						//超過選擇上限報錯
						if(contentArr.length > question.get().getSelectLimit()) {
							return new AnswerResp(RtnCode.EXCEED_SELECT_LIMIT.getCode(), 
									RtnCode.EXCEED_SELECT_LIMIT.getMessage(), false);
						}
					}
				}else {
					//非必填
					//若有填答且是複選題就要檢查是否超過選擇上限
					if(StringUtils.hasText(thisAnswerReq.getContent()) && question.get().getType().equals("select") 
							&& question.get().isMultipleChoice() == true) {
						System.out.println("是複選");
						String content = thisAnswerReq.getContent();
						String[] contentArr = content.split(",");
						//超過選擇上限報錯
						if(contentArr.length > question.get().getSelectLimit()) {
							return new AnswerResp(RtnCode.EXCEED_SELECT_LIMIT.getCode(), 
									RtnCode.EXCEED_SELECT_LIMIT.getMessage(), false);
						}
					}
				}
				Answer answer = new Answer(questionnaire.get(), question.get(), respondent.get(),
						thisAnswerReq.getContent());
				answers.add(answer);
			}
			answerDao.saveAll(answers);
		}
		return new AnswerResp(RtnCode.ADD_SUCCESSFUL.getCode(), 
				RtnCode.ADD_SUCCESSFUL.getMessage(), true);
	}

	@Override
	public AnswerResp getAllAnswer() {
		List<Answer> answers = answerDao.findAll();
		return new AnswerResp(answers, RtnCode.GET_SUCCESSFUL.getCode(), RtnCode.GET_SUCCESSFUL.getMessage(), true);
	}

	@Override
	public AnswerResp getAnswersOfQuestion(Long questionId) {
		if(questionId < 1) {
			return new AnswerResp(RtnCode.QUESTIONID_ILLEGAL.getCode(), 
					RtnCode.QUESTIONID_ILLEGAL.getMessage(), false);
		}
		Optional<Question> question = questionDao.findById(questionId);
		if(question.isEmpty()) {
			return new AnswerResp(RtnCode.QUESTION_NOT_EXISTS.getCode(), 
					RtnCode.QUESTION_NOT_EXISTS.getMessage(), false);
		}
		List<Answer> answers = answerDao.getAnswersOfQuestion(questionId);
		if(answers.size() == 0) {
			return new AnswerResp(RtnCode.ANSWERS_NOT_EXISTS.getCode(), 
					RtnCode.ANSWERS_NOT_EXISTS.getMessage(), false);
		}
		return new AnswerResp(answers, RtnCode.GET_SUCCESSFUL.getCode(), RtnCode.GET_SUCCESSFUL.getMessage(), true);
	}

	@Override
	public AnswerResp getSomeonesAnswersOfQuestionnaire(Long userId, Long questionnaireId) {
		if(userId < 1) {
			return new AnswerResp(RtnCode.USERID_ILLEGAL.getCode(), 
					RtnCode.USERID_ILLEGAL.getMessage(), false);
		}
		Optional<User> user = userDao.findById(userId);
		if(user.isEmpty()) {
			return new AnswerResp(RtnCode.USER_NOT_EXISTS.getCode(), 
					RtnCode.USER_NOT_EXISTS.getMessage(), false);
		}
		if(questionnaireId < 1) {
			return new AnswerResp(RtnCode.QUESTIONNAIREID_ILLEGAL.getCode(), 
					RtnCode.QUESTIONNAIREID_ILLEGAL.getMessage(), false);
		}
		Optional<Questionnaire> Questionnaire = questionnaireDao.findById(questionnaireId);
		if(Questionnaire.isEmpty()) {
			return new AnswerResp(RtnCode.QUESTIONNAIRE_NOT_EXISTS.getCode(), 
					RtnCode.QUESTIONNAIRE_NOT_EXISTS.getMessage(), false);
		}
		List<Answer> answers = answerDao.getSomeonesAnswersOfQuestionnaire(userId , questionnaireId);
		if(answers.size() == 0) {
			return new AnswerResp(RtnCode.THIS_QUESTIONNAIREID_AND_USER_ANSWERS_NOT_EXISTS.getCode(), 
					RtnCode.THIS_QUESTIONNAIREID_AND_USER_ANSWERS_NOT_EXISTS.getMessage(), false);
		}
		return new AnswerResp(answers, RtnCode.GET_SUCCESSFUL.getCode(), RtnCode.GET_SUCCESSFUL.getMessage(), true);
	}

}
