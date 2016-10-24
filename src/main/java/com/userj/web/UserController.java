package com.userj.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	private List<User> users = new ArrayList<User>();

	@PostMapping("/create")
	public String create(User user){
		System.out.println(user);
		users.add(user);
		return "redirect:/list"; //회원가입후 list로 이동
	}
	
	@GetMapping("/list")
	public String list(Model model){
		model.addAttribute("users", users);
		return "list";
	}

}
