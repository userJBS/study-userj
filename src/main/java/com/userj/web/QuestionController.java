package com.userj.web;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	// 글 리스트
	@GetMapping("/list")
	public String list() {
		return "/index";
	}

	// 글 등록
	@GetMapping("/create")
	public String create(HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}

		return "/qna/create";
	}

	// 글 상세보기
	@GetMapping("/show/{id}")
	public String show(@PathVariable Long id, Model model) {
		model.addAttribute("question", questionRepository.findOne(id));
		return "/qna/show";
	}

	// 업데이트
	@GetMapping("/{id}/update")
	public String update(@PathVariable Long id, Model model, HttpSession session) {
		// 로그인이 안된 상태일 경우
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}
		
		User sessionedUser= HttpSessionUtils.getUserFromSession(session);
		Question question = questionRepository.findOne(id);
		
		System.out.println(sessionedUser);
		
		// 로그인 되어 있는 글 작성자 인지 체크한다.
		if(!question.isSameWriter(sessionedUser)){
			return String.format("redirect:/questions/show/%d", id);
		}
		
		model.addAttribute("question", question);
		return "/qna/updateForm";
	}

	// 삭제
	@GetMapping("/{id}/delete")
	public String delete(@PathVariable Long id) {
		questionRepository.delete(id);
		return "redirect:/";
	}

	// ------------Form-----------끝

	// TODO 2. ------------로직 처리 ( POST 처리 )-----------시작
	@PostMapping("/create")
	public String create(Question quesion, HttpSession session) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login";
		}

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, quesion.getTitle(), quesion.getContents());
		questionRepository.save(newQuestion);

		return "redirect:/";
	}

	@PostMapping("/{id}/update")
	public String update(@PathVariable Long id, Question newQuestion) {
		Question question = questionRepository.findOne(id);
		question.update(newQuestion.getTitle(), newQuestion.getContents());
		questionRepository.save(question);
		return String.format("redirect:/questions/show/%d/", id);
	}
	// ------------로직-----------끝

}
