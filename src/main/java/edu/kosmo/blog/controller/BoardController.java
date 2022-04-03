package edu.kosmo.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import edu.kosmo.blog.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	

	@GetMapping({"","/"})
	public String index(Model model, @PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
		//index 페이지로 boards가 날라감. 
		model.addAttribute("boards",boardService.글목록(pageable));
		return "index"; 
	}
	//@pathvariable 는 Spring3에서 추가된 기능 중 하나에요. 어떤 기능이냐구요? 말 그대로 URL 경로에 변수를 넣어주는거에요
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/detail";		
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/updateForm";	
	}
	
	//USER 권한이 필요
	@GetMapping({"/board/saveForm"})
	public String saveForm() {
		return "board/saveForm"; 
	}
}
