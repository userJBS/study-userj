package com.userj.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	// 첫페이지
	@GetMapping("")
	public String home() {
		return "index"; //  templates/index.html 파일 접근
	} 
}
