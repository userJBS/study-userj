package com.userj.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
public class Question {
	
	@Id
	@GeneratedValue
	private Long id;
	private String writer;
	private String title;
	private String contents;
	
	
	public Question(String writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	
	
}
