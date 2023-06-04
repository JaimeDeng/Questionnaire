package com.example.Questionnaire.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "question")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private Long questionId;
	
	@ManyToOne
	@JsonProperty("questionnaireId")
	@JoinColumn(name = "questionnaire_id" , referencedColumnName = "questionnaire_id", insertable = true, updatable = true)
	private Questionnaire questionnaire;	//questionnaireId欄位
	
	@Column(name = "question")
	private String question;
	
	@Column(name = "options")
	private String options;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "required")
	private boolean required;
	
	@Column(name = "multiple_choice")
	private boolean multipleChoice;
	
	@Column(name = "select_limit")
	private Integer selectLimit;
	
	//================questionId 外鍵關聯=================
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
	@JsonIgnore
	private List<Answer> answers;
	
	
	
	
	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isMultipleChoice() {
		return multipleChoice;
	}

	public void setMultipleChoice(boolean multipleChoice) {
		this.multipleChoice = multipleChoice;
	}

	public Integer getSelectLimit() {
		return selectLimit;
	}

	public void setSelectLimit(Integer selectLimit) {
		this.selectLimit = selectLimit;
	}

	@JsonIgnore
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	public Question() {
	}
	
	public Question(Questionnaire questionnaire, String question, String options, String type,
			boolean required, boolean multipleChoice, Integer selectLimit) {
		this.questionnaire = questionnaire;
		this.question = question;
		this.options = options;
		this.type = type;
		this.required = required;
		this.multipleChoice = multipleChoice;
		this.selectLimit = selectLimit;
	}

	public Question(Long questionId, Questionnaire questionnaire, String question, String options, String type,
			boolean required, boolean multipleChoice, Integer selectLimit) {
		this.questionId = questionId;
		this.questionnaire = questionnaire;
		this.question = question;
		this.options = options;
		this.type = type;
		this.required = required;
		this.multipleChoice = multipleChoice;
		this.selectLimit = selectLimit;
	}
	 
}
