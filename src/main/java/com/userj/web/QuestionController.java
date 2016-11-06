package com.userj.web;

import javax.servlet.http.HttpSession;

import org.h2.jdbc.JdbcSQLException;
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
	// 3. 리팩토링

	// TODO 1. ------------ Form 연결 URL ( GET 처리 )------------시작

	// 글 리스트
	@GetMapping("/list")
	public String list() {
		return "/index";
	}

	// 글 등록
	@GetMapping("/create")
	public String create(HttpSession session) {
		// hasPernission(session);

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
		Question question = questionRepository.findOne(id);
		Result result = valid(session, question);

		if (!result.isValid()) {
			model.addAttribute("erreoMessage", result.getErrorMessage());
			return "/user/login";
		}

		model.addAttribute("question", question);
		return "/qna/updateForm";
	}

	// 삭제
	@GetMapping("/{id}/delete")
	public String delete(@PathVariable Long id, Model model, HttpSession session) {
		
		Question question = questionRepository.findOne(id);
		
		// Result 클래스는 에러 메시지를 처리한다.
		Result result = valid(session, question);
		// 로그인 또는 세션 유저와 현제 글을 삭제하려는 유저를 비교한다.
		if (!result.isValid()) {
			model.addAttribute("erreoMessage", result.getErrorMessage());
			return "/user/login";
		}
		
		try {
			questionRepository.delete(id);
			// TODO FK 키설정으로 의견이 있는 글 삭제시 처리필요.
		} catch (Exception e) {
			model.addAttribute("erreoMessage", "의견이 달린 글은 삭제할수 없습니다.");
			return "/user/login";
		}
		return "redirect:/";
	}

	// ------------Form-----------끝

	// TODO 2. ------------로직 처리 ( POST 처리 )-----------시작
	@PostMapping("/create")
	public String create(Question quesion, HttpSession session) {
		// hasPernission(session);

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		Question newQuestion = new Question(sessionUser, quesion.getTitle(), quesion.getContents());
		questionRepository.save(newQuestion);

		return "redirect:/";
	}

	@PostMapping("/{id}/update")
	public String update(@PathVariable Long id, Question newQuestion, Model model, HttpSession session) {
		Question question = questionRepository.findOne(id);
		Result result = valid(session, question);

		if (!result.isValid()) {
			model.addAttribute("erreoMessage", result.getErrorMessage());
			return "/user/login";
		}

		question.update(newQuestion.getTitle(), newQuestion.getContents());
		questionRepository.save(question);
		model.addAttribute("question", question);
		return String.format("redirect:/questions/show/%d/", id);

	}
	// ------------로직-----------끝

	// TODO 3. 리팩토링 시작
	private Result valid(HttpSession session, Question question) {
		if (!HttpSessionUtils.isLoginUser(session)) {
			return Result.fail("로그인이 필요합니다.");
		}

		// 세션에 있는 유저와 현제 로그인된 유저가 같은 유저인지 확인한다.
		User loginUser = HttpSessionUtils.getUserFromSession(session);
		if (!question.isSameWriter(loginUser)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}

		return Result.ok();
	}

	// ------------리팩토링-----------끝

}
