package com.userj.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping("/helloworld")  //get 방식으로 보낸다.
	public String welcome(String name,int age, Model model) { // 메서드 명의미 없음
		System.out.println(name);
		// 리턴 되는 값은 파일명 (파일 확장자 스프링이 알아서)
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		return "welcome";
	}
}
