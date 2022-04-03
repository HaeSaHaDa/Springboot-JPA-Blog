package edu.kosmo.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.kosmo.blog.config.auth.PrincipalDetail;
import edu.kosmo.blog.dto.ResponseDto;
import edu.kosmo.blog.model.Board;
import edu.kosmo.blog.service.BoardService;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService; 
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println("컨텐츠는???" + board);
		boardService.글쓰기(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteByid(@PathVariable int id){
		boardService.글삭제하기(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board){
			boardService.글수정하기(id, board);
			return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
