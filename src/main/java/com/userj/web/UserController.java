package com.userj.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.userj.domain.User;
import com.userj.domain.UserRepository;

@Controller
public class UserController {
	private List<User> users = new ArrayList<User>();

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/create")
	public String create(User user) {
		System.out.println(user);
		userRepository.save(user); // 받아온 값들을 DB에 저장.
		return "redirect:/list"; // 회원가입후 list로 이동
	}

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("users", userRepository.findAll());
		return "list";
	}

}
