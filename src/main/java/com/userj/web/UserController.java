package com.userj.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userj.domain.User;
import com.userj.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// TODO UserController 역할
	// 1. Form 관련 URL ( 폼에 연결 또는 작은 로직을 담고 있다.)
	// 2. 핵심 요청 처리를 하는 로직 구현

	// TODO 1. ------------Form 관련 URL------------시작

	// 회원 리스트
	@GetMapping("/list") // list.html 에서 Get으로 값이 들어오면 모든 회원 리스트를 보여준다
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list"; // templates/user/list.html 뷰
	}

	// 회원 가입
	@GetMapping("/create")
	public String form() {
		return "/user/create";
	}

	// 로그인
	@GetMapping("/login") // /users/loginForm 요청시 로그인 화면으로 이동
	public String loginForm() {
		return "/user/login";
	}

	// 로그아웃
	@GetMapping("/logout") // /users/logout 요청시 세션 값 SessionUser 삭제
	public String logout(HttpSession session) {
		session.removeAttribute("sessionUser");
		return "redirect:/";
	}

	// 회원 정보 수정
	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {

		if (HttpSessionUtils.isLoginUser(session)) { // 세션값이 없을 경우 로그인화면으로 이동
			return "redirect:/users/login";
		}

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		if (!id.equals(sessionUser.getId())) { // 세션 값과 파라미터에서 받아온 id값 비교
			throw new IllegalStateException("Session value mismatch!");
		}

		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		return "/user/update"; // 아이디가 존재할 경우에만 수정 페이지로 이동
	}

	// ------------Form-----------끝

	
	// TODO 2. ------------로직-----------시작

	// 회원 가입 요청 처리
	@PostMapping("create") // form.html 에서 Post로 값이 들어오면 회원가입
	public String create(User user) {
		System.out.println(user);
		userRepository.save(user); // 받아온 값들을 DB에 저장.
		return "redirect:/users/list"; // GET 속성으로 users URL로 이동
	}

	// 로그인 요청 처리
	@PostMapping("/login") // /users/login 요청시(로그인시도) 세션 값 SessionUser 생성
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);

		if (user == null) {
			return "redirect:/users/login"; // 아이디가 없을 경우.
		}

		//!password.equals(user.getPassword()
		if (user.machPassword(password)) {
			return "redirect:/users/login"; // 비밀번호가 다를 경우.
		}

		// 세션에 user 저장
		session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

		return "redirect:/";
	}

	// 회원가입 폼에서 > 회원가입 요청 처리
	@PostMapping("/update/{id}")
	public String update(@PathVariable Long id, User updateUser, HttpSession session) {

		if (HttpSessionUtils.isLoginUser(session)) {
			return "redirect:/users/login"; // 세션 값이 없을경우
		}

		User sessionUser = HttpSessionUtils.getUserFromSession(session);
		if (!id.equals(sessionUser.getId())) {  
			throw new IllegalStateException("You cat't update the anther user!");
		}

		User user = userRepository.findOne(id);
		user.update(updateUser);
		userRepository.save(user);
		return "redirect:/users/list"; // users URL로 이동
	}

	// ------------로직-----------끝

}
