package com.example.Questionnaire.services;

import com.example.Questionnaire.vo.UserReq;
import com.example.Questionnaire.vo.UserResp;

public interface UserService {
	
	public UserResp createUser (UserReq userReq);
	public UserResp editUser (UserReq userReq);
	public UserResp deleteUser (UserReq userReq);
	public UserResp getUserByUserId (Long userId);
	public UserResp getUserByAccount (String account);
	public UserResp getAllUser ();
	
}
