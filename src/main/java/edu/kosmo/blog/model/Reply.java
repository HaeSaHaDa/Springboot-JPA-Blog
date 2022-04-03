package edu.kosmo.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reply{

	@Id//Primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;//시퀀스, auto_increment
	
	@Column(nullable = false, length=200)
	private String content; 
	
	//누가 어느 테이블에 있는 답변인가??? 연관관계가 필요해.
	@ManyToOne//n:1 Reply와 Board는 n:1 관계
	@JoinColumn(name="boardId")//FK 컬럼
	private Board board;
	
	@ManyToOne//Reply와 User는 n:1
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
