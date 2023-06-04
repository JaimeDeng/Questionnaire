package com.example.Questionnaire.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.*;

import com.example.Questionnaire.entity.User;

@Repository
@Qualifier("userDao")
public interface UserDao extends JpaRepository<User, Long> {
	
	User getUserByAccount(String account);

}
