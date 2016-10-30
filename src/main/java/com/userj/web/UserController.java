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


	@PostMapping
	public String create(User user) {
		System.out.println(user);
		userRepository.save(user); // 받아온 값들을 DB에 저장.
		return "redirect:/users"; // users URL로 이동
	}
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list"; // templates/user/list.html 뷰
	}

	@GetMapping("/loginForm")
	public String loginForm(){
		return "/user/login";
	}
	
	@PostMapping("/login")
	public String login(String userId, String password, HttpSession session){
		User user = userRepository.findByUserId(userId);
		
		if(user==null){
			return "redirect:/users/loginForm";
		}
		
		if(!password.equals(user.getPassword())){
			return "redirect:/users/loginForm";
		}
		
		// 세션에 user 저장
		session.setAttribute("user", user);
		
		return "redirect:/";
	}
	
	@GetMapping("/form")
	public String form() {
		return "/user/form";
	}


	@GetMapping("/{id}/form")
	public String updateForm(@PathVariable Long id, Model model) {
		model.addAttribute("user", userRepository.findOne(id));
		return "/user/updateForm";
	}
	
	@PutMapping("/{id}")
	public String update(@PathVariable Long id, User newUser) {
		User user = userRepository.findOne(id);
		user.update(newUser);
		userRepository.save(user);
		return "redirect:/users"; // users URL로 이동
	}

}
