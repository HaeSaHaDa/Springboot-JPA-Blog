package edu.kosmo.blog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.kosmo.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;

//인증이 안 된 사용자들이 출입할 수있는 경로를 /auth/** 허용
// 그냥 주소가 /이면 index.jsp 허용
//static 이하에 있는 /js/**, /css/**, /image/** 허용
@Slf4j
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	@GetMapping("/user/updatePassword")
	public String updatePasswordForm() {
		return "user/updatePassword";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}
	

}
