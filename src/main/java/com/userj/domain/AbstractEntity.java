package com.userj.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@MappedSuperclass
//객체 끼리 비교할때 id 만 같은면 true로 설정하겠다.
@EqualsAndHashCode(of="id")  
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

	@Id
	@GeneratedValue
	@JsonProperty
	@Getter
	private Long id;

	@CreatedDate // 데이터가 생성된 시간
	private LocalDateTime createDate;

	@LastModifiedDate // 데이터가 수정된 시간
	private LocalDateTime modifiedDate;

	
	public boolean machId(Long newId) {
		if (newId == null) {
			return false;
		}
		return newId == id;
	}
	
	public String getFormattedCreateDate() {
		return getFormattedDate(createDate);
	}
	public String getFormattedModifiedDate() {
		return getFormattedDate(modifiedDate);
	}

	private String getFormattedDate(LocalDateTime dateTime) {
		if (dateTime == null) {
			return "";
		}
		return dateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
	}
}
