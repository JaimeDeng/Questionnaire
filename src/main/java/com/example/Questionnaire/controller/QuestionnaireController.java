package com.example.Questionnaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.example.Questionnaire.services.AnswerService;
import com.example.Questionnaire.services.QuestionService;
import com.example.Questionnaire.services.QuestionnaireService;
import com.example.Questionnaire.services.UserService;
import com.example.Questionnaire.vo.AnswerReq;
import com.example.Questionnaire.vo.AnswerResp;
import com.example.Questionnaire.vo.QuestionReq;
import com.example.Questionnaire.vo.QuestionResp;
import com.example.Questionnaire.vo.QuestionnaireReq;
import com.example.Questionnaire.vo.QuestionnaireResp;
import com.example.Questionnaire.vo.UserReq;
import com.example.Questionnaire.vo.UserResp;

@RestController
@CrossOrigin
public class QuestionnaireController {

	private UserService userService;
	private QuestionnaireService questionnaireService;
	private QuestionService questionService;
	private AnswerService answerService;
	
	@Autowired
	public QuestionnaireController (@Qualifier("userService")UserService userService , 
			@Qualifier("questionnaireService")QuestionnaireService questionnaireService,
			@Qualifier("questionService")QuestionService questionService,
			@Qualifier("answerService")AnswerService answerService) {
		this.userService = userService;
		this.questionnaireService = questionnaireService;
		this.questionService = questionService;
		this.answerService = answerService;
	}
	
//----------------------------------------User API------------------------------------------
	
	//創建用戶
	@PostMapping(value = "/createUser" , produces = "application/json;charset=UTF-8")
	public UserResp createUser(@RequestBody UserReq userReq) {
		return userService.createUser(userReq);
	}
	
	//以帳號獲取用戶資訊
	@GetMapping(value = "/getUserByAccount/{account}" , produces = "application/json;charset=UTF-8")
	public UserResp getUserByAccount(@PathVariable String account) {
		return userService.getUserByAccount(account);
	}
	
	//以ID獲取用戶資訊
	@GetMapping(value = "/getUserByUserId/{userId}" , produces = "application/json;charset=UTF-8")
	public UserResp getUserByUserId(@PathVariable Long userId) {
		return userService.getUserByUserId(userId);
	}
	
	//獲取所有用戶資訊
	@GetMapping(value = "/getAllUser" , produces = "application/json;charset=UTF-8")
	public UserResp getUserByAccount() {
		return userService.getAllUser();
	}
	
	//編輯用戶
	@PutMapping(value = "/editUser" , produces = "application/json;charset=UTF-8")
	public UserResp editUser(@RequestBody UserReq userReq) {
		return userService.editUser(userReq);
	}
	
	//刪除用戶(disable)
	@PutMapping(value = "/deleteUser" , produces = "application/json;charset=UTF-8")
	public UserResp deleteUser(@RequestBody UserReq userReq) {
		return userService.deleteUser(userReq);
	}
	
//----------------------------------------Questionnaire API------------------------------------------
	
	//創建問卷
	@PostMapping(value = "/createQuestionnaire" , produces = "application/json;charset=UTF-8")
	public QuestionnaireResp createQuestionnaire(@RequestBody QuestionnaireReq questionnaireReq) {
		return questionnaireService.createQuestionnaire(questionnaireReq);
	}
	
	//編輯問卷
	@PutMapping(value = "/editQuestionnaire" , produces = "application/json;charset=UTF-8")
	public QuestionnaireResp editQuestionnaire(@RequestBody QuestionnaireReq questionnaireReq) {
		return questionnaireService.editQuestionnaire(questionnaireReq);
	}
	
	//獲取所有問卷資訊
	@GetMapping(value = "/getAllQuestionnaire" , produces = "application/json;charset=UTF-8")
	public QuestionnaireResp getAllQuestionnaire() {
		return questionnaireService.getAllQuestionnaire();
	}
	
	//獲取問卷資料量
	@GetMapping(value = "/getQuestionnairesDataNum" , produces = "application/json;charset=UTF-8")
	public QuestionnaireResp getQuestionnairesDataNum() {
		return questionnaireService.getQuestionnairesDataNum();
	}
	
	//獲取問卷指定頁面資料
	@GetMapping(value = "/getQuestionnairesByPage/{page}" , produces = "application/json;charset=UTF-8")
	public QuestionnaireResp getQuestionnairesByPage(@PathVariable Integer page) {
		return questionnaireService.getQuestionnairesByPage(page);
	}
	
	//獲取指定ID問卷資訊
	@GetMapping(value = "/getQuestionnaireByQuestionnaireId/{questionnaireId}" , produces = "application/json;charset=UTF-8")
	public QuestionnaireResp getQuestionnaireByQuestionnaireId(@PathVariable Long questionnaireId) {
		return questionnaireService.getQuestionnaireByQuestionnaireId(questionnaireId);
	}
	
	//獲取關鍵字標題問卷資訊數量
	@GetMapping(value = "/getQuestionnaireNumByKeyword/{keyword}" , produces = "application/json;charset=UTF-8")
	public QuestionnaireResp getQuestionnaireNumByKeyword(@PathVariable String keyword) {
		return questionnaireService.getQuestionnaireNumByKeyword(keyword);
	}
	
	//獲取關鍵字標題問卷資訊
	@GetMapping(value = "/getQuestionnaireByKeyword/{keyword}" , produces = "application/json;charset=UTF-8")
	public QuestionnaireResp getQuestionnaireByKeyword(@PathVariable String keyword) {
		return questionnaireService.getQuestionnaireByKeyword(keyword);
	}
	
	//獲取關鍵字標題問卷資訊並分頁
	@GetMapping(value = "/getQuestionnaireByKeywordAsPage/{keyword}/{page}" , produces = "application/json;charset=UTF-8")
	public QuestionnaireResp getQuestionnaireByKeywordAsPage(@PathVariable String keyword , 
			@PathVariable Integer page) {
		return questionnaireService.getQuestionnaireByKeywordAsPage(page , keyword);
	}
	
	//刪除指定ID問卷資訊
	@DeleteMapping(value = "/deleteQuestionnaire/{questionnaireId}" , produces = "application/json;charset=UTF-8")
	public QuestionnaireResp deleteQuestionnaire(@PathVariable Long questionnaireId) {
		return questionnaireService.deleteQuestionnaire(questionnaireId);
	}
	
//--------------------------------------------Question API----------------------------------------------
	
	//創建問題
	@PostMapping(value = "/createQuestion" , produces = "application/json;charset=UTF-8")
	public QuestionResp createQuestion(@RequestBody QuestionReq questionReq) {
		return questionService.createQuestion(questionReq);
	}
	
	//編輯問題
	@PutMapping(value = "/editQuestion" , produces = "application/json;charset=UTF-8")
	public QuestionResp editQuestion(@RequestBody QuestionReq questionReq) {
		return questionService.editQuestion(questionReq);
	}
	
	//獲取所有問提資訊
	@GetMapping(value = "/getAllQuestion" , produces = "application/json;charset=UTF-8")
	public QuestionResp getAllQuestion() {
		return questionService.getAllQuestion();
	}
	
	//獲取指定問卷的所有問題
	@GetMapping(value = "/getQuestionsOfQuestionnaire/{questionnaireId}" , produces = "application/json;charset=UTF-8")
	public QuestionResp getQuestionsOfQuestionnaire(@PathVariable Long questionnaireId) {
		return questionService.getQuestionsOfQuestionnaire(questionnaireId);
	}
	
//---------------------------------------------Answer API---------------------------------------------
	
	//創建答案
	@PostMapping(value = "/createAnswer" , produces = "application/json;charset=UTF-8")
	public AnswerResp createAnswer(@RequestBody AnswerReq answerReq) {
		return answerService.createAnswer(answerReq);
	}
	
	//獲取全部答案
	@GetMapping(value = "/getAllAnswer" , produces = "application/json;charset=UTF-8")
	public AnswerResp getAllAnswer() {
		return answerService.getAllAnswer();
	}
	
	//獲取指定問題的全部答案
	@GetMapping(value = "/getAnswersOfQuestion/{questionId}" , produces = "application/json;charset=UTF-8")
	public AnswerResp getAnswersOfQuestion(@PathVariable Long questionId) {
		return answerService.getAnswersOfQuestion(questionId);
	}
	
	//獲取指定人員指定問卷的全部答案
	@GetMapping(value = "/getSomeonesAnswersOfQuestionnaire/{userId}/{questionnaireId}" , produces = "application/json;charset=UTF-8")
	public AnswerResp getSomeonesAnswersOfQuestionnaire(@PathVariable Long userId , @PathVariable Long questionnaireId) {
		return answerService.getSomeonesAnswersOfQuestionnaire(userId , questionnaireId);
	}
}
