package com.userj.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.userj.domain.QuestionRepository;

@Controller
public class HomeController {

	@Autowired
	private QuestionRepository questionRepository;

	// 첫페이지
	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("questions", questionRepository.findAll());
		return "index"; // templates/index.html 파일 접근
	}
}
