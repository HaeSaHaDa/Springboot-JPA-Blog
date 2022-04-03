package edu.kosmo.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.kosmo.blog.model.Board;



public interface BoardRepository extends JpaRepository<Board, Integer>{

}

