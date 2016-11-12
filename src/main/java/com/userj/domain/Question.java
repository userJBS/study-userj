package com.userj.domain;

import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@ToString
@NoArgsConstructor
public class Question extends AbstractEntity {

	@ManyToOne // 질문(N) : 회원 (1) 다:1 관계 FK 설정
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer")) // FK이름설정
	@JsonProperty
	private User writer;
	@JsonProperty
	private String title;
	@Lob
	@JsonProperty
	private String contents;

	// 답변 갯수
	@JsonProperty
	private Integer countOfAnswer = 0;

	@OneToMany(mappedBy = "question") // mappedBy="필드 이름"
	@OrderBy("id ASC") // id 를 기준으로 오름 차순된다.
	@JsonIgnore
	private List<Answer> answers;

	public void addAnswer() {
		this.countOfAnswer += 1;
	}

	public void deleteAnswer() {
		this.countOfAnswer -= 1;
	}

	

	public Question(User writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	// (수정, 삭제) 글 작성자인지 체크한다.
	public boolean isSameWriter(User sessionedUser) {
		return this.writer.machPassword(sessionedUser);
		// return this.writer.getPassword().equals(sessionedUser.getPassword());
	}

}
