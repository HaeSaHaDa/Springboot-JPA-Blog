package edu.kosmo.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML 파일)
//@Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

	//http요청 실습
	// http://localhost:8282/http/get(select)
	@GetMapping("/http/get")
	public String getTest(Member m) {
		return "get 요청: " + m.getId() + "," + m.getUsername() + "," + m.getEmail();
	}
	
	// http://localhost:8282/http/post(insert)
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) {
		return "post 요청: "  + m.getId() + "," + m.getUsername() + "," + m.getPassword()+ "," + m.getEmail();
	}
	
	 
	
	//http://localhost:8282/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
