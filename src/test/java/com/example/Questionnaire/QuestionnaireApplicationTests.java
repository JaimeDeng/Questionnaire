package com.example.Questionnaire;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.Questionnaire.repository.QuestionnaireDao;

@SpringBootTest
class QuestionnaireApplicationTests {
	
	@Autowired
	QuestionnaireDao questionnaireDao;

	@Test
	void contextLoads() {
	}

}
