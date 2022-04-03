package edu.kosmo.blog.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.kosmo.blog.dto.ResponseDto;
import edu.kosmo.blog.model.User;
import edu.kosmo.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {

		System.out.println("UserApiController: 호출됨-회원가입");
		userService.회원가입(user);
		// (정상적인 연결인지, DB에 insert 하고나서 리턴된 결과값을 넣을 거.)
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);// 자바 객체를 JSON으로 변환해서 리턴(Jackson)
	}

	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) {
		userService.회원수정(user);
		// 여기서는 트랜잭션이 종료, DB에 값은 변경이 됐음
		// 하지만 세션 값은 변경이 되지 않음. 우리가 직접 세션 값을 변경해 줄 것.
		// 스프링 시큐리티가 어떻게 로그인 되는지, 로그인 될 때 세션이 어떻게 만들어 지는지 개념이 필요.
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		System.out.println(authentication.getPrincipal());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@PutMapping("/user/password")
	public ResponseDto<Integer> passwordupdate(@RequestBody User user) {
		userService.회원비밀번호수정(user);
		// 여기서는 트랜잭션이 종료, DB에 값은 변경이 됐음
		// 하지만 세션 값은 변경이 되지 않음. 우리가 직접 세션 값을 변경해 줄 것.
		// 스프링 시큐리티가 어떻게 로그인 되는지, 로그인 될 때 세션이 어떻게 만들어 지는지 개념이 필요.
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@PostMapping("/logoutsuccess")
	public String logoutsuccess(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("로그아웃이 되나???");
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/today";
	}
}
