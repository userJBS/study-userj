package com.userj.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userj.domain.Answer;
import com.userj.domain.AnswerRepository;
import com.userj.domain.Question;
import com.userj.domain.QuestionRepository;
import com.userj.domain.User;

@Controller
@RequestMapping("/qustions/{questionId}/answers")
public class AnswerController {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;

	@PostMapping("/create")
	public String create(@PathVariable Long questionId, String contents, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		Question question=questionRepository.findOne(questionId);
		Answer answer=new Answer(loginUser, question, contents);
		answerRepository.save(answer);
		return String.format("redirect:/questions/show/%d", questionId);
	}

}
