package com.example.Questionnaire.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.Questionnaire.constants.RtnCode;
import com.example.Questionnaire.entity.Questionnaire;
import com.example.Questionnaire.entity.User;
import com.example.Questionnaire.repository.QuestionnaireDao;
import com.example.Questionnaire.repository.UserDao;
import com.example.Questionnaire.vo.QuestionnaireReq;
import com.example.Questionnaire.vo.QuestionnaireResp;

@Service
@Qualifier("questionnaireService")
public class QuestionnaireServiceImpl implements QuestionnaireService {

	private  QuestionnaireDao questionnaireDao;
	private UserDao userDao;
	
	@Autowired
	public QuestionnaireServiceImpl (@Qualifier("questionnaireDao")QuestionnaireDao questionnaireDao , 
			@Qualifier("userDao")UserDao userDao) {
		this.questionnaireDao = questionnaireDao;
		this.userDao = userDao;
	}
	
	//新增問卷
	@Override
	public QuestionnaireResp createQuestionnaire(QuestionnaireReq questionnaireReq) {
		if(!StringUtils.hasText(questionnaireReq.getTitle())) {
			return new QuestionnaireResp(RtnCode.TITLE_CANNOT_EMPTY.getCode() 
					, RtnCode.TITLE_CANNOT_EMPTY.getMessage() , false);
		}
		if(!StringUtils.hasText(questionnaireReq.getDescription())) {
			return new QuestionnaireResp(RtnCode.DESCRIPTION_CANNOT_EMPTY.getCode() 
					, RtnCode.DESCRIPTION_CANNOT_EMPTY.getMessage() , false);
		}
		Optional<User> user = userDao.findById(questionnaireReq.getCreator());
		if(user.isEmpty()) {
			return new QuestionnaireResp(RtnCode.USER_NOT_EXISTS.getCode() 
					, RtnCode.USER_NOT_EXISTS.getMessage() , false);
		}
		if(!StringUtils.hasText(questionnaireReq.getStartTime())) {
			return new QuestionnaireResp(RtnCode.STARTTIME_CANNOT_EMPTY.getCode() 
					, RtnCode.STARTTIME_CANNOT_EMPTY.getMessage() , false);
		}
		if(!StringUtils.hasText(questionnaireReq.getEndTime())) {
			return new QuestionnaireResp(RtnCode.ENDTIME_CANNOT_EMPTY.getCode() 
					, RtnCode.ENDTIME_CANNOT_EMPTY.getMessage() , false);
		}
		if(questionnaireReq.getStartTime().equals(questionnaireReq.getEndTime())) {
			return new QuestionnaireResp(RtnCode.SAMETIME_ERROR.getCode() 
					, RtnCode.SAMETIME_ERROR.getMessage() , false);
		}
		
		if(questionnaireReq.getStartTime().length() != 10 || questionnaireReq.getEndTime().length() != 10) {
        	return new QuestionnaireResp(RtnCode.DATE_ILLEGAL.getCode() 
					, RtnCode.DATE_ILLEGAL.getMessage() , false);
		}
		//檢驗日期格式 & 次序
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);	//設定日期檢驗模式為嚴格 , 不可有寬鬆模式容忍2月30這樣的狀況
        try {
        	Date startDate = dateFormat.parse(questionnaireReq.getStartTime());
        	Date endDate = dateFormat.parse(questionnaireReq.getEndTime());
        	
        	if(endDate.before(startDate)) {
        		return new QuestionnaireResp(RtnCode.DATE_SEQUENCE_ERROE.getCode() 
    					, RtnCode.DATE_SEQUENCE_ERROE.getMessage() , false);
        	}
        } catch (java.text.ParseException e) {	//字串轉換Date格式失敗視為無效日期
        	return new QuestionnaireResp(RtnCode.DATE_ILLEGAL.getCode() 
					, RtnCode.DATE_ILLEGAL.getMessage() , false);
        }
        
        Questionnaire questionnaire = new Questionnaire(questionnaireReq.getTitle() , questionnaireReq.getDescription() , 
        		user.get() , questionnaireReq.getStartTime() , questionnaireReq.getEndTime());
        questionnaireDao.save(questionnaire);
		return new QuestionnaireResp(RtnCode.ADD_SUCCESSFUL.getCode() 
				, RtnCode.ADD_SUCCESSFUL.getMessage() , true);
	}

	//編輯問卷
	@Override
	public QuestionnaireResp editQuestionnaire(QuestionnaireReq questionnaireReq) {
		Optional<Questionnaire> existsQuestionnaire = questionnaireDao.findById(questionnaireReq.getQuestionnaireId());
		if(existsQuestionnaire.isEmpty()) {
			return new QuestionnaireResp(RtnCode.QUESTIONNAIRE_NOT_EXISTS.getCode() 
					, RtnCode.QUESTIONNAIRE_NOT_EXISTS.getMessage() , false);
		}
		if(!StringUtils.hasText(questionnaireReq.getTitle())) {
			return new QuestionnaireResp(RtnCode.TITLE_CANNOT_EMPTY.getCode() 
					, RtnCode.TITLE_CANNOT_EMPTY.getMessage() , false);
		}
		if(!StringUtils.hasText(questionnaireReq.getDescription())) {
			return new QuestionnaireResp(RtnCode.DESCRIPTION_CANNOT_EMPTY.getCode() 
					, RtnCode.DESCRIPTION_CANNOT_EMPTY.getMessage() , false);
		}
		Optional<User> user = userDao.findById(questionnaireReq.getCreator());
		if(user.isEmpty()) {
			return new QuestionnaireResp(RtnCode.USER_NOT_EXISTS.getCode() 
					, RtnCode.USER_NOT_EXISTS.getMessage() , false);
		}
		if(!StringUtils.hasText(questionnaireReq.getStartTime())) {
			return new QuestionnaireResp(RtnCode.STARTTIME_CANNOT_EMPTY.getCode() 
					, RtnCode.STARTTIME_CANNOT_EMPTY.getMessage() , false);
		}
		if(!StringUtils.hasText(questionnaireReq.getEndTime())) {
			return new QuestionnaireResp(RtnCode.ENDTIME_CANNOT_EMPTY.getCode() 
					, RtnCode.ENDTIME_CANNOT_EMPTY.getMessage() , false);
		}
		if(questionnaireReq.getStartTime().equals(questionnaireReq.getEndTime())) {
			return new QuestionnaireResp(RtnCode.SAMETIME_ERROR.getCode() 
					, RtnCode.SAMETIME_ERROR.getMessage() , false);
		}
		
		if(questionnaireReq.getStartTime().length() != 10 || questionnaireReq.getEndTime().length() != 10) {
        	return new QuestionnaireResp(RtnCode.DATE_ILLEGAL.getCode() 
					, RtnCode.DATE_ILLEGAL.getMessage() , false);
		}
		
		//檢驗日期格式 & 次序
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);	//設定日期檢驗模式為嚴格 , 不可有寬鬆模式容忍2月30這樣的狀況
        try {
        	Date startDate = dateFormat.parse(questionnaireReq.getStartTime());
        	Date endDate = dateFormat.parse(questionnaireReq.getEndTime());
        	
        	if(endDate.before(startDate)) {
        		return new QuestionnaireResp(RtnCode.DATE_SEQUENCE_ERROE.getCode() 
    					, RtnCode.DATE_SEQUENCE_ERROE.getMessage() , false);
        	}
        } catch (java.text.ParseException e) {	//字串轉換Date格式失敗視為無效日期
        	return new QuestionnaireResp(RtnCode.DATE_ILLEGAL.getCode() 
					, RtnCode.DATE_ILLEGAL.getMessage() , false);
        }
        
        Questionnaire questionnaire = new Questionnaire(questionnaireReq.getQuestionnaireId() , questionnaireReq.getTitle() , 
        		questionnaireReq.getDescription() , user.get() , questionnaireReq.getStartTime() , questionnaireReq.getEndTime());
        questionnaireDao.save(questionnaire);
		return new QuestionnaireResp(RtnCode.EDIT_SUCCESSFUL.getCode() 
				, RtnCode.EDIT_SUCCESSFUL.getMessage() , true);
	}

	//獲取全部問卷
	@Override
	public QuestionnaireResp getAllQuestionnaire() {
		List<Questionnaire>questionnaires = questionnaireDao.findAll();
		if(questionnaires.size() == 0) {
        	return new QuestionnaireResp(RtnCode.QUESTIONNAIRE_NOT_EXISTS.getCode() 
					, RtnCode.QUESTIONNAIRE_NOT_EXISTS.getMessage() , false);
		}
		 QuestionnaireResp questionnaireResp = new  QuestionnaireResp();
		 questionnaireResp.setQuestionnaires(questionnaires);
		 questionnaireResp.code = RtnCode.GET_SUCCESSFUL.getCode();
		 questionnaireResp.message = RtnCode.GET_SUCCESSFUL.getMessage();
		 questionnaireResp.success = true;
		return questionnaireResp;
	}

	//獲取指定ID問卷
	@Override
	public QuestionnaireResp getQuestionnaireByQuestionnaireId(Long questionnaireId) {
		if(questionnaireId < 1) {
	       	return new QuestionnaireResp(RtnCode.QUESTIONNAIREID_ILLEGAL.getCode() 
						, RtnCode.QUESTIONNAIREID_ILLEGAL.getMessage() , false);
		}
		Optional<Questionnaire> questionnaire = questionnaireDao.findById(questionnaireId);
		if(questionnaire.isEmpty()) {
	       	return new QuestionnaireResp(RtnCode.QUESTIONNAIRE_NOT_EXISTS.getCode() 
						, RtnCode.QUESTIONNAIRE_NOT_EXISTS.getMessage() , false);
		}
		 QuestionnaireResp questionnaireResp = new  QuestionnaireResp();
		 questionnaireResp.setQuestionnaireId(questionnaireId);
		 questionnaireResp.setTitle(questionnaire.get().getTitle());
		 questionnaireResp.setDescription(questionnaire.get().getDescription());
		 questionnaireResp.setStartTime(questionnaire.get().getStartTime());
		 questionnaireResp.setEndTime(questionnaire.get().getEndTime());
		 questionnaireResp.setUser(questionnaire.get().getUser());
		 questionnaireResp.code = RtnCode.GET_SUCCESSFUL.getCode();
		 questionnaireResp.message = RtnCode.GET_SUCCESSFUL.getMessage();
		 questionnaireResp.success = true;
		 return questionnaireResp;
	}

	//刪除問卷
	@Override
	public QuestionnaireResp deleteQuestionnaire(Long questionnaireId) {
		if(questionnaireId < 1) {
	       	return new QuestionnaireResp(RtnCode.QUESTIONNAIREID_ILLEGAL.getCode() 
						, RtnCode.QUESTIONNAIREID_ILLEGAL.getMessage() , false);
		}
		Optional<Questionnaire> questionnaire = questionnaireDao.findById(questionnaireId);
		if(questionnaire.isEmpty()) {
	       	return new QuestionnaireResp(RtnCode.QUESTIONNAIRE_NOT_EXISTS.getCode() 
						, RtnCode.QUESTIONNAIRE_NOT_EXISTS.getMessage() , false);
		}
		questionnaireDao.delete(questionnaire.get());
		return new QuestionnaireResp(RtnCode.DELETE_SUCCESSFUL.getCode() 
				, RtnCode.DELETE_SUCCESSFUL.getMessage() , true);
	}

	@Override
	public QuestionnaireResp getQuestionnaireByKeyword(String keyword) {
		List<Questionnaire> questionnaires = questionnaireDao.getQuestionnaireByKeyword(keyword);
		return new QuestionnaireResp(questionnaires, RtnCode.GET_SUCCESSFUL.getCode() , 
				RtnCode.GET_SUCCESSFUL.getMessage(), true);
	}

}
