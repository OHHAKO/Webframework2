package kr.ac.hansung.cse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public String login(@RequestParam(value="error", required=false)String error,
						@RequestParam(value="logout", required=false)String logout,
			Model model) {
		//error 라는 이름의 리퀘스트에서 넘어오는 값을 받겠다는거고 String error 로 넘어오게 된다. 모델 정의하면 스프링에서 만들어서 넣어줌
		if(error!=null) {
			model.addAttribute("errorMsg","Invalid username and password");
		}
		
		if(logout!=null) {
			model.addAttribute("logoutMsg","You have been logged out successfully");
		}
		
		return "login";
	}
	
}
