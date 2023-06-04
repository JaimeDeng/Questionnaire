package com.example.Questionnaire.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.Questionnaire.constants.RtnCode;
import com.example.Questionnaire.entity.User;
import com.example.Questionnaire.repository.UserDao;
import com.example.Questionnaire.vo.UserReq;
import com.example.Questionnaire.vo.UserResp;

@Service
@Qualifier("userService")
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	@Autowired
	public UserServiceImpl (@Qualifier("userDao")UserDao userDao) {
		this.userDao = userDao;
	}

	//創建用戶
	@Override
	public UserResp createUser(UserReq userReq) {
		List<User> users = userDao.findAll();
		for(User user : users) {
			if(user.getAccount().equals(userReq.getAccount())) {
				return new UserResp(RtnCode.ACCOUNT_HAS_BEEN_USED.getCode() 
						, RtnCode.ACCOUNT_HAS_BEEN_USED.getMessage() , false);
			}
		}
		if(!StringUtils.hasText(userReq.getAccount())) {
			return new UserResp(RtnCode.ACCOUNT_CANNOT_EMPTY.getCode() 
					, RtnCode.ACCOUNT_CANNOT_EMPTY.getMessage() , false);
		}
		String accountPattern = "[a-zA-Z0-9]{8,10}";
		if(!userReq.getAccount().matches(accountPattern)) {
			return new UserResp(RtnCode.ACCOUNT_ILLEGAL.getCode() 
					, RtnCode.ACCOUNT_ILLEGAL.getMessage() , false);
		}
		if(!StringUtils.hasText(userReq.getPassword())) {
			return new UserResp(RtnCode.PWD_CANNOT_EMPTY.getCode() 
					, RtnCode.PWD_CANNOT_EMPTY.getMessage() , false);
		}
		String pwdPattern = "[a-zA-Z0-9]{8,20}";
		if(!userReq.getPassword().matches(pwdPattern)) {
			return new UserResp(RtnCode.PWD_ILLEGAL.getCode() 
					, RtnCode.PWD_ILLEGAL.getMessage() , false);
		}
		if(!StringUtils.hasText(userReq.getName())) {
			return new UserResp(RtnCode.NAME_CANNOT_EMPTY.getCode() 
					, RtnCode.NAME_CANNOT_EMPTY.getMessage() , false);
		}
		if(userReq.getName().length() < 1 || userReq.getName().length() > 20) {
			return new UserResp(RtnCode.NAME_ILLEGAL.getCode() 
					, RtnCode.NAME_ILLEGAL.getMessage() , false);
		}
		
		User user = new User(userReq.getAccount() , userReq.getPassword() , userReq.getName());
		userDao.save(user);
		
		return new UserResp(RtnCode.ADD_SUCCESSFUL.getCode()
				, RtnCode.ADD_SUCCESSFUL.getMessage() , true);
	}

	
	//編輯用戶
	@Override
	public UserResp editUser(UserReq userReq) {
		Optional<User> user = userDao.findById(userReq.getUserId());
		if(user.isEmpty()) {
			return new UserResp(RtnCode.USER_NOT_EXISTS.getCode() 
					, RtnCode.USER_NOT_EXISTS.getMessage() , false);
		}
		List<User> users = userDao.findAll();
		for(User oneUser : users) {
			if(oneUser.getAccount().equals(userReq.getAccount())) {
				return new UserResp(RtnCode.ACCOUNT_HAS_BEEN_USED.getCode() 
						, RtnCode.ACCOUNT_HAS_BEEN_USED.getMessage() , false);
			}
		}
		if(!StringUtils.hasText(userReq.getAccount())) {
			return new UserResp(RtnCode.ACCOUNT_CANNOT_EMPTY.getCode() 
					, RtnCode.ACCOUNT_CANNOT_EMPTY.getMessage() , false);
		}
		String accountPattern = "[a-zA-Z0-9]{8,10}";
		if(!userReq.getAccount().matches(accountPattern)) {
			return new UserResp(RtnCode.ACCOUNT_ILLEGAL.getCode() 
					, RtnCode.ACCOUNT_ILLEGAL.getMessage() , false);
		}
		if(!StringUtils.hasText(userReq.getPassword())) {
			return new UserResp(RtnCode.PWD_CANNOT_EMPTY.getCode() 
					, RtnCode.PWD_CANNOT_EMPTY.getMessage() , false);
		}
		String pwdPattern = "[a-zA-Z0-9]{8,20}";
		if(!userReq.getPassword().matches(pwdPattern)) {
			return new UserResp(RtnCode.PWD_ILLEGAL.getCode() 
					, RtnCode.PWD_ILLEGAL.getMessage() , false);
		}
		if(!StringUtils.hasText(userReq.getName())) {
			return new UserResp(RtnCode.NAME_CANNOT_EMPTY.getCode() 
					, RtnCode.NAME_CANNOT_EMPTY.getMessage() , false);
		}
		if(userReq.getName().length() < 1 || userReq.getName().length() > 20) {
			return new UserResp(RtnCode.NAME_ILLEGAL.getCode() 
					, RtnCode.NAME_ILLEGAL.getMessage() , false);
		}
		
		User updateUser = new User(userReq.getUserId() , userReq.getAccount() , userReq.getPassword() , 
				userReq.getName());
		userDao.save(updateUser);
		
		return new UserResp(RtnCode.EDIT_SUCCESSFUL.getCode()
				, RtnCode.EDIT_SUCCESSFUL.getMessage() , true);
	}

	
	//刪除用戶(disable , 非真正刪除)
	@Override
	public UserResp deleteUser(UserReq userReq) {
		Optional<User> user = userDao.findById(userReq.getUserId());
		if(user.isEmpty()) {
			return new UserResp(RtnCode.USER_NOT_EXISTS.getCode() 
					, RtnCode.USER_NOT_EXISTS.getMessage() , false);
		}
		User updateUser = user.get();
		updateUser.setEnable(false);
		userDao.save(updateUser);
		return new UserResp(RtnCode.DELETE_SUCCESSFUL.getCode() 
				, RtnCode.DELETE_SUCCESSFUL.getMessage() , true);
	}

	
	//以帳號獲取用戶資訊
	@Override
	public UserResp getUserByAccount(String account) {
		User user = userDao.getUserByAccount(account);
		if(!StringUtils.hasText(account)) {
			return new UserResp(RtnCode.ACCOUNT_CANNOT_EMPTY.getCode() 
					, RtnCode.ACCOUNT_CANNOT_EMPTY.getMessage() , false);
		}
		if(user == null) {
			return new UserResp(RtnCode.ACCOUNT_NOT_EXISTS.getCode()
					, RtnCode.ACCOUNT_NOT_EXISTS.getMessage() , true);
		}
		UserResp userResp = new UserResp();
		userResp.setUserId(user.getUserId());
		userResp.setName(user.getName());
		userResp.setAccount(account);
		userResp.setPassword(user.getPassword());
		userResp.code = RtnCode.GET_SUCCESSFUL.getCode();
		userResp.message = RtnCode.GET_SUCCESSFUL.getMessage();
		userResp.success = true;
		return userResp;
	}

	
	//獲取所有用戶資訊
	@Override
	public UserResp getAllUser() {
		List<User> users = userDao.findAll();
		if(users.size() == 0) {
			return new UserResp(RtnCode.ACCOUNTS_NOT_EXISTS.getCode()
					, RtnCode.ACCOUNTS_NOT_EXISTS.getMessage() , false);
		}
		UserResp userResp = new UserResp();
		userResp.setUsers(users);
		userResp.code = RtnCode.GET_SUCCESSFUL.getCode();
		userResp.message = RtnCode.GET_SUCCESSFUL.getMessage();
		userResp.success = true;
		return userResp;
	}

	@Override
	public UserResp getUserByUserId(Long userId) {
		Optional<User> user = userDao.findById(userId);
		if(userId < 1) {
			return new UserResp(RtnCode.USERID_ILLEGAL.getCode() 
					, RtnCode.USERID_ILLEGAL.getMessage() , false);
		}
		if(user.isEmpty()) {
			return new UserResp(RtnCode.USER_NOT_EXISTS.getCode()
					, RtnCode.USER_NOT_EXISTS.getMessage() , true);
		}
		UserResp userResp = new UserResp();
		userResp.setUserId(userId);
		userResp.setName(user.get().getName());
		userResp.setAccount(user.get().getAccount());
		userResp.setPassword(user.get().getPassword());
		userResp.code = RtnCode.GET_SUCCESSFUL.getCode();
		userResp.message = RtnCode.GET_SUCCESSFUL.getMessage();
		userResp.success = true;
		return userResp;
	}

}
