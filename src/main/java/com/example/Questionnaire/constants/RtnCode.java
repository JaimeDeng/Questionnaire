package com.example.Questionnaire.constants;

public enum RtnCode {

	ADD_SUCCESSFUL("200" , "資料新增成功"),
	GET_SUCCESSFUL("200" , "資料獲取成功"),
	EDIT_SUCCESSFUL("200" , "資料修改成功"),
	DELETE_SUCCESSFUL("200" , "資料刪除成功"),
	//User
	USER_NOT_EXISTS("400" , "無此使用者存在"),
	USERID_ILLEGAL("400" , "使用者ID不得小於1"),
	//Account
	ACCOUNT_CANNOT_EMPTY("400" , "帳號不得為空"),
	ACCOUNT_ILLEGAL("400" , "帳號格式錯誤 , 必須為英數字8~10字符"),
	ACCOUNT_NOT_EXISTS("400" , "此帳號不存在"),
	ACCOUNTS_NOT_EXISTS("400" , "無任何帳號資訊"),
	ACCOUNT_HAS_BEEN_USED("400" , "此帳號已被使用"),
	USER_HAS_ACCOUNT("400" ,"此使用者已有帳號"),
	//Pwd
	PWD_CANNOT_EMPTY("400" , "密碼不得為空"),
	PWD_CANNOT_SAME("400" , "新密碼不得與舊密碼相同"),
	PWD_ILLEGAL("400" , "密碼格式錯誤 , 必須為英數字8~20字符"),
	//Name
	NAME_CANNOT_EMPTY("400" , "名稱不得為空"),
	NAME_ILLEGAL("400" , "名稱格式錯誤 , 必須為1~20字符"),
	//問卷
	QUESTIONNAIREID_ILLEGAL("400" , "問卷ID不得小於1"),
	TITLE_CANNOT_EMPTY("400" , "標題不得為空"),
	DESCRIPTION_CANNOT_EMPTY("400" , "問卷說明不得為空"),
	STARTTIME_CANNOT_EMPTY("400" , "開始時間不得為空"),
	ENDTIME_CANNOT_EMPTY("400" , "結束時間不得為空"),
	SAMETIME_ERROR("400" , "開始時間不得與結束時間相同"),
	DATE_ILLEGAL("400" , "時間格式錯誤"),
	DATE_SEQUENCE_ERROE("400" , "結束日期不得早於開始日期"),
	QUESTIONNAIRE_NOT_EXISTS("400" , "無任何問卷資訊"),
	//問題
	QUESTIONID_ILLEGAL("400" , "問題ID不得小於1"),
	QUESTION_CANNOT_EMPTY("400" , "標題不得為空"),
	QUESTION_NOT_EXISTS("400" , "無此問題資訊"),
	QUESTIONS_NOT_EXISTS("400" , "無任何問題資訊"),
	OPTIONS_CANNOT_EMPTY("400" , "問題為選擇題時選項不得為空"),
	OPTIONS_CANNOT_INPUT("400" , "問題為簡答題時選項不得填寫"),
	TYPE_CANNOT_EMPTY("400" , "問題類型不得為空"),
	SELECT_LIMIT_SET_WRONG("400" , "選擇限制數設定異常"),
	//答案
	ANSWER_CANNOT_EMPTY("400" , "答案不可空白"),
	ANSWERS_NOT_EXISTS("400" , "無此問題的答案存在"),
	THIS_QUESTIONNAIREID_AND_USER_ANSWERS_NOT_EXISTS("400" , "無此使用者之指定問卷的答案存在"),
	EXCEED_SELECT_LIMIT("400" , "超過選擇上限");
	
	private String code;
	
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	private RtnCode(String message) {
		this.message = message;
	}
	
	private RtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
