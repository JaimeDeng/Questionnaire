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
import com.example.Questionnaire.entity.Question;
import com.example.Questionnaire.entity.Questionnaire;
import com.example.Questionnaire.repository.QuestionDao;
import com.example.Questionnaire.repository.QuestionnaireDao;
import com.example.Questionnaire.repository.UserDao;
import com.example.Questionnaire.vo.QuestionReq;
import com.example.Questionnaire.vo.QuestionResp;

@Service
@Qualifier("questionService")
public class QuestionServiceImpl implements QuestionService {
	
	private QuestionnaireDao questionnaireDao;
	private QuestionDao questionDao;
	
	@Autowired
	public QuestionServiceImpl (@Qualifier("questionnaireDao")QuestionnaireDao questionnaireDao , 
			@Qualifier("userDao")UserDao userDao ,@Qualifier("questionDao")QuestionDao questionDao) {
		this.questionnaireDao = questionnaireDao;
		this.questionDao = questionDao;
	}

	@Override
	@Transactional
	public QuestionResp createQuestion(QuestionReq questionReq) {
		List<QuestionReq>questionsReq = questionReq.getQuestionsReq();
		if(questionsReq.size() == 0) {
			//單個存取
			if(!StringUtils.hasText(questionReq.getQuestion())) {
				return new QuestionResp(RtnCode.QUESTION_CANNOT_EMPTY.getCode(), 
						RtnCode.QUESTION_CANNOT_EMPTY.getMessage(), false);
			}
			Optional<Questionnaire> questionnaire = questionnaireDao.findById(questionReq.getQuestionnaireId());
			if(questionnaire.isEmpty()) {
				return new QuestionResp(RtnCode.QUESTIONNAIRE_NOT_EXISTS.getCode(), 
						RtnCode.QUESTIONNAIRE_NOT_EXISTS.getMessage(), false);
			}
			if(!StringUtils.hasText(questionReq.getType())) {
				return new QuestionResp(RtnCode.TYPE_CANNOT_EMPTY.getCode(), 
						RtnCode.TYPE_CANNOT_EMPTY.getMessage(), false);
			}
			//如果是選擇題那options就不可為null
			if(questionReq.getType() == "select" && !StringUtils.hasText(questionReq.getOptions())) {
				return new QuestionResp(RtnCode.OPTIONS_CANNOT_EMPTY.getCode(), 
						RtnCode.OPTIONS_CANNOT_EMPTY.getMessage(), false);
			}
			//如果是簡答題那options就必須為null
			if(questionReq.getType() == "short-answer" && StringUtils.hasText(questionReq.getOptions())) {
				return new QuestionResp(RtnCode.OPTIONS_CANNOT_INPUT.getCode(), 
						RtnCode.OPTIONS_CANNOT_INPUT.getMessage(), false);
			}
			if(questionReq.isMultipleChoice() == false && questionReq.getSelectLimit() != null) {
				//設定為單選題但卻輸入設定選擇上限
				return new QuestionResp(RtnCode.SELECT_LIMIT_SET_WRONG.getCode(), 
						RtnCode.SELECT_LIMIT_SET_WRONG.getMessage(), false);
			}
			if(questionReq.isMultipleChoice() == true && questionReq.getSelectLimit() == null 
					|| questionReq.getSelectLimit() < 2) {
				//設定為複選題但卻沒設定選則上限或上限小於2
				return new QuestionResp(RtnCode.SELECT_LIMIT_SET_WRONG.getCode(), 
						RtnCode.SELECT_LIMIT_SET_WRONG.getMessage(), false);
			}
			
			Question question = new Question(questionnaire.get() , questionReq.getQuestion() , questionReq.getOptions() , 
					questionReq.getType() , questionReq.isRequired() , questionReq.isMultipleChoice() , questionReq.getSelectLimit());
			questionDao.save(question);
			return new QuestionResp(RtnCode.ADD_SUCCESSFUL.getCode(), 
					RtnCode.ADD_SUCCESSFUL.getMessage(), true);
		}else {
			List<Question>questions = new ArrayList<>();
			for(QuestionReq thisQuestionReq : questionsReq) {
				if(!StringUtils.hasText(thisQuestionReq.getQuestion())) {
					return new QuestionResp(RtnCode.QUESTION_CANNOT_EMPTY.getCode(), 
							RtnCode.QUESTION_CANNOT_EMPTY.getMessage(), false);
				}
				Optional<Questionnaire> questionnaire = questionnaireDao.findById(thisQuestionReq.getQuestionnaireId());
				if(questionnaire.isEmpty()) {
					return new QuestionResp(RtnCode.QUESTIONNAIRE_NOT_EXISTS.getCode(), 
							RtnCode.QUESTIONNAIRE_NOT_EXISTS.getMessage(), false);
				}
				if(!StringUtils.hasText(thisQuestionReq.getType())) {
					return new QuestionResp(RtnCode.TYPE_CANNOT_EMPTY.getCode(), 
							RtnCode.TYPE_CANNOT_EMPTY.getMessage(), false);
				}
				//如果是選擇題那options就不可為null
				if(thisQuestionReq.getType() == "select" && !StringUtils.hasText(thisQuestionReq.getOptions())) {
					return new QuestionResp(RtnCode.OPTIONS_CANNOT_EMPTY.getCode(), 
							RtnCode.OPTIONS_CANNOT_EMPTY.getMessage(), false);
				}
				//如果是簡答題那options就必須為null
				if(thisQuestionReq.getType() == "short-answer" && StringUtils.hasText(thisQuestionReq.getOptions())) {
					return new QuestionResp(RtnCode.OPTIONS_CANNOT_INPUT.getCode(), 
							RtnCode.OPTIONS_CANNOT_INPUT.getMessage(), false);
				}
				if(thisQuestionReq.isMultipleChoice() == false && thisQuestionReq.getSelectLimit() != null) {
					//設定為單選題但卻輸入設定選擇上限
					return new QuestionResp(RtnCode.SELECT_LIMIT_SET_WRONG.getCode(), 
							RtnCode.SELECT_LIMIT_SET_WRONG.getMessage(), false);
				}
				if(thisQuestionReq.isMultipleChoice() == true && thisQuestionReq.getSelectLimit() == null 
						| thisQuestionReq.getSelectLimit() < 2) {
					//設定為複選題但卻沒設定選則上限或上限小於2
					return new QuestionResp(RtnCode.SELECT_LIMIT_SET_WRONG.getCode(), 
							RtnCode.SELECT_LIMIT_SET_WRONG.getMessage(), false);
				}
				
				Question question = new Question(questionnaire.get() , thisQuestionReq.getQuestion() , thisQuestionReq.getOptions() , 
						thisQuestionReq.getType() , thisQuestionReq.isRequired() , thisQuestionReq.isMultipleChoice() , thisQuestionReq.getSelectLimit());
				questions.add(question);
			}
			questionDao.saveAll(questions);
		}
		return new QuestionResp(RtnCode.ADD_SUCCESSFUL.getCode(), 
				RtnCode.ADD_SUCCESSFUL.getMessage(), true);
	}

	@Override
	public QuestionResp editQuestion(QuestionReq questionReq) {
		if(questionReq.getQuestionId() < 1) {
			return new QuestionResp(RtnCode.QUESTIONID_ILLEGAL.getCode(), 
					RtnCode.QUESTIONID_ILLEGAL.getMessage(), false);
		}
		Optional<Question> question = questionDao.findById(questionReq.getQuestionId());
		if(question.isEmpty()) {
			return new QuestionResp(RtnCode.QUESTION_NOT_EXISTS.getCode(), 
					RtnCode.QUESTION_NOT_EXISTS.getMessage(), false);
		}
		if(!StringUtils.hasText(questionReq.getQuestion())) {
			return new QuestionResp(RtnCode.QUESTION_CANNOT_EMPTY.getCode(), 
					RtnCode.QUESTION_CANNOT_EMPTY.getMessage(), false);
		}
		Optional<Questionnaire> questionnaire = questionnaireDao.findById(questionReq.getQuestionnaireId());
		if(questionnaire.isEmpty()) {
			return new QuestionResp(RtnCode.QUESTIONNAIRE_NOT_EXISTS.getCode(), 
					RtnCode.QUESTIONNAIRE_NOT_EXISTS.getMessage(), false);
		}
		if(!StringUtils.hasText(questionReq.getType())) {
			return new QuestionResp(RtnCode.TYPE_CANNOT_EMPTY.getCode(), 
					RtnCode.TYPE_CANNOT_EMPTY.getMessage(), false);
		}
		//如果是選擇題那options就不可為null
		if(questionReq.getType() == "select" && !StringUtils.hasText(questionReq.getOptions())) {
			return new QuestionResp(RtnCode.OPTIONS_CANNOT_EMPTY.getCode(), 
					RtnCode.OPTIONS_CANNOT_EMPTY.getMessage(), false);
		}
		//如果是簡答題那options就必須為null
		if(questionReq.getType() == "short-answer" && StringUtils.hasText(questionReq.getOptions())) {
			return new QuestionResp(RtnCode.OPTIONS_CANNOT_INPUT.getCode(), 
					RtnCode.OPTIONS_CANNOT_INPUT.getMessage(), false);
		}
		if(questionReq.isMultipleChoice() == false && questionReq.getSelectLimit() != null) {
			//設定為單選題但卻輸入設定選擇上限
			return new QuestionResp(RtnCode.SELECT_LIMIT_SET_WRONG.getCode(), 
					RtnCode.SELECT_LIMIT_SET_WRONG.getMessage(), false);
		}
		if(questionReq.isMultipleChoice() == true && questionReq.getSelectLimit() == null 
				|| questionReq.getSelectLimit() < 2) {
			//設定為複選題但卻沒設定選則上限或上限小於2
			return new QuestionResp(RtnCode.SELECT_LIMIT_SET_WRONG.getCode(), 
					RtnCode.SELECT_LIMIT_SET_WRONG.getMessage(), false);
		}
		
		Question newQuestion = new Question(questionReq.getQuestionId() , questionnaire.get() 
				, questionReq.getQuestion() , questionReq.getOptions() , questionReq.getType() , 
				questionReq.isRequired() , questionReq.isMultipleChoice() , questionReq.getSelectLimit());
		questionDao.save(newQuestion);
		return new QuestionResp(RtnCode.EDIT_SUCCESSFUL.getCode(), 
				RtnCode.EDIT_SUCCESSFUL.getMessage(), true);
	}

	@Override
	public QuestionResp getAllQuestion() {
		List<Question> questions = questionDao.findAll();
		if(questions.size() == 0) {
			return new QuestionResp(RtnCode.QUESTIONS_NOT_EXISTS.getCode(), 
					RtnCode.QUESTIONS_NOT_EXISTS.getMessage(), false);
		}
		return new QuestionResp(questions , RtnCode.GET_SUCCESSFUL.getCode(), 
				RtnCode.GET_SUCCESSFUL.getMessage(), true);
	}

	@Override
	public QuestionResp getQuestionsOfQuestionnaire(Long questionnaireId) {
		Optional<Questionnaire> questionnaire = questionnaireDao.findById(questionnaireId);
		if(questionnaire.isEmpty()) {
			return new QuestionResp(RtnCode.QUESTIONNAIRE_NOT_EXISTS.getCode(), 
					RtnCode.QUESTIONNAIRE_NOT_EXISTS.getMessage(), false);
		}
		List<Question> questions = questionDao.getQuestionsOfQuestionnaire(questionnaireId);
		if(questions.size() == 0) {
			return new QuestionResp(RtnCode.QUESTIONS_NOT_EXISTS.getCode(), 
					RtnCode.QUESTIONS_NOT_EXISTS.getMessage(), false);
		}
		return new QuestionResp(questions , RtnCode.GET_SUCCESSFUL.getCode(), 
				RtnCode.GET_SUCCESSFUL.getMessage(), true);
	}

}
