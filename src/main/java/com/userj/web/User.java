package com.userj.web;

import lombok.Data;

@Data
public class User {
	private String userId;
	private String password;
	private String name;
	private String email;
	
	//setter..  toString 부분
}
