package com.userj.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter // ( 롬복 셋팅이 필요)
@ToString(exclude = "password")
@Entity // 데이터 베이스와 매핑하게된다.
public class User {

	@Id // primary key키설정 (고유값이 된다.)
	@GeneratedValue // 자동으로 값을 1씩 증가시켜준다.
	@JsonProperty  // json 요청시 응답할 데이터
	private Long id;

	@Column(length = 20) // 최대 길이 20으로 설정
	@NotBlank // null, "", "(space)" 세가지 조건 값들을 허용하지 않는다.
	@JsonProperty
	private String userId;
	@JsonIgnore
	private String password;
	@JsonProperty
	private String name;
	@JsonProperty
	private String email;

	public void update(User newUser) {
		this.password = newUser.password;
		this.name = newUser.name;
		this.email = newUser.email;
	}

	public boolean machId(Long newId) {
		if (newId == null) {
			return false;
		}
		return newId == id;
	}

	// 아래 오버로딩 된 메서드 리팩토링 필요.
	// 패스워드를 노출시키지 않기위해 User 엔티티가 변경됬다.
	public boolean machPassword(User sessionedUser) {
		if (sessionedUser == null) {
			return false;
		}
		return this.password.equals(sessionedUser.password);
	}

	public boolean machPassword(String sessionedUser) {
		if (sessionedUser == null) {
			return false;
		}
		return sessionedUser.equals(password);
	}

}
