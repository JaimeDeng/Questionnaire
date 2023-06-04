package com.example.Questionnaire.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "questionnaire")
public class Questionnaire {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "questionnaire_id")
	private Long questionnaireId;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JsonProperty("creator")
	@JoinColumn(name = "creator" , referencedColumnName = "user_id", insertable = true, updatable = true)
	private User user;	//creator欄位
	
	@Column(name = "start_time")
	private String startTime;
	
	@Column(name = "end_time")
	private String endTime;
	
	//================questionnaireId 外鍵關聯=================
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "questionnaire")
	@JsonIgnore
	private List<Question> questions;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "questionnaire")
	@JsonIgnore
	private List<Answer> answers;

	
	public Long getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Long questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@JsonIgnore
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Questionnaire() {
	}
	
	public Questionnaire(String title, String description, User user, String startTime,
			String endTime) {
		this.title = title;
		this.description = description;
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Questionnaire(Long questionnaireId, String title, String description, User user, String startTime,
			String endTime) {
		this.questionnaireId = questionnaireId;
		this.title = title;
		this.description = description;
		this.user = user;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
}
