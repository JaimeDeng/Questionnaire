package com.example.Questionnaire.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "answer")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id")
	private Long answerId;
	
	@ManyToOne
	@JsonProperty("questionnaireId")
	@JoinColumn(name = "questionnaire_id" , referencedColumnName = "questionnaire_id", insertable = true, updatable = true)
	private Questionnaire questionnaire;	//questionnaireId欄位
	
	@ManyToOne
	@JsonProperty("question_id")
	@JoinColumn(name = "question_id" , referencedColumnName = "question_id", insertable = true, updatable = true)
	private Question question;	//questionId欄位
	
	@ManyToOne
	@JsonProperty("respondent")
	@JoinColumn(name = "respondent" , referencedColumnName = "user_id", insertable = true, updatable = true)
	private User user;	//questionId欄位
	
	@Column(name = "content")
	private String content;

	
	
	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public Answer() {
	}
	
	public Answer(Questionnaire questionnaire, Question question, User user,
			String content) {
		this.questionnaire = questionnaire;
		this.question = question;
		this.user = user;
		this.content = content;
	}

	public Answer(Long answerId, Questionnaire questionnaire, Question question, User user,
			String content) {
		this.answerId = answerId;
		this.questionnaire = questionnaire;
		this.question = question;
		this.user = user;
		this.content = content;
	}
	
}
