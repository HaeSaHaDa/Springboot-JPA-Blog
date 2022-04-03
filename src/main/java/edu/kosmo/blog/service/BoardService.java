package edu.kosmo.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kosmo.blog.model.Board;
import edu.kosmo.blog.model.User;
import edu.kosmo.blog.repository.BoardRepository;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	

	//프린시펄이 들고 있는 유저 오브젝트 필요
	
	@Transactional
	public void 글쓰기(Board board, User user) {//title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
				});
	}
	@Transactional
	public void 글삭제하기(int id) {
		System.out.println("글삭제하기: "+id);
		 boardRepository.deleteById(id);

	}
	//글 수정을 위해서는 영속화 필요.
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
				});//영속화 완료.
		
		board.setTitle( requestBoard.getTitle());
		board.setContent( requestBoard.getContent());
		//해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료됩니다. 이 때 더티체킹이 일어나면서 자동 업데이트가 됨. DB로 flush(commit)
		
	}
}
