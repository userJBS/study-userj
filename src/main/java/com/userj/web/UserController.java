package com.userj.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userj.domain.User;
import com.userj.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// form.html 에서 Post로 값이 들어오면 회원가입
	@PostMapping
	public String create(User user) {
		System.out.println(user);
		userRepository.save(user); // 받아온 값들을 DB에 저장.
		return "redirect:/users"; // users URL로 이동
	}

	// 모든 회원 정보 리스트
	@GetMapping
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list"; // templates/user/list.html 뷰
	}

	// /users/loginForm 요청시 로그인 화면으로 이동
	@GetMapping("/loginForm")
	public String loginForm() {
		return "/user/login";
	}

	// /users/login 요청시 세션 값 SessionUser 생성
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session) {
		User user = userRepository.findByUserId(userId);

		if (user == null) {
			return "redirect:/users/loginForm";
		}

		if (!password.equals(user.getPassword())) {
			return "redirect:/users/loginForm";
		}

		// 세션에 user 저장
		session.setAttribute("sessionUser", user);

		return "redirect:/";
	}

	// /users/logout 요청시 세션 값 SessionUser 삭제
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessionUser");
		return "redirect:/";
	}

	// 회원 가입폼
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}

	// 자신의 정보 수정
	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
		Object tempUser = session.getAttribute("sessionUser");

		if (tempUser == null) {
			return "redirect:/users/form";
		}

		User sessionUser = (User) tempUser;
		if (!id.equals(sessionUser.getId())) {
			throw new IllegalStateException("You cat't update the anther user!");
		}

		User user = userRepository.findOne(id);
		model.addAttribute("user", user);
		return "/user/updateForm";
	}

	// 회원 정보 수정
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User updateUser, HttpSession session) {
		Object tempUser = session.getAttribute("sessionUser");

		if (tempUser == null) {
			return "redirect:/users/form";
		}

		User sessionUser = (User) tempUser;
		if (!id.equals(sessionUser.getId())) {
			throw new IllegalStateException("You cat't update the anther user!");
		}
		User user = userRepository.findOne(id);
		user.update(updateUser);
		userRepository.save(user);
		return "redirect:/users"; // users URL로 이동
	}
	

}
