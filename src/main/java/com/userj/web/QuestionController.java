package com.userj.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userj.domain.Question;
import com.userj.domain.QuestionRepository;
import com.userj.domain.User;

@Controller
@RequestMapping("/questions")
public class QuestionController {
	
	@Autowired
	private QuestionRepository questionRepository;  
	
	// TODO 역할 !
	// 1. Form 연결 URL ( GET 처리 )
	// 2. 로직 처리 ( POST 처리 )

	// TODO 1. ------------ Form 연결 URL ( GET 처리 )------------시작
	@GetMapping("list")
	public String list() {
		return "/index";
	}

	@GetMapping("create")
	public String create(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}

		return "/qna/create";
	}

	// ------------Form-----------끝

	// TODO 2. ------------로직 처리 ( POST 처리 )-----------시작
	@PostMapping("create")
	public String create(Question quesion, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion=new Question(sessionUser.getUserId(), quesion.getTitle(), quesion.getContents());
		questionRepository.save(newQuestion);

		return "redirect:/";
	}

	// ------------로직-----------끝

}
