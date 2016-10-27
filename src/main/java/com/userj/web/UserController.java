package com.userj.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userj.domain.User;
import com.userj.domain.UserRepository;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/form")
	public String form(){
		return "/user/form";
	}
	
	@PostMapping
	public String create(User user) {
		System.out.println(user);
		userRepository.save(user); // 받아온 값들을 DB에 저장.
		return "redirect:/users"; // users URL로 이동
	}
	

	@GetMapping
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "/user/list";  // templates/user/list.html 뷰
	}

}
