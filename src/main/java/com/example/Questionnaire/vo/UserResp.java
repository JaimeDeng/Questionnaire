package com.example.Questionnaire.vo;

import java.util.List;

import com.example.Questionnaire.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResp extends User {

	public String code;
	
	public String message;
	
	public boolean success;
	
	private List<User> users;
	
	
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public UserResp(String code , String message , boolean success) {
		this.code = code;
		this.message = message;
		this.success = success;
	}

	public UserResp() {
	}

}
