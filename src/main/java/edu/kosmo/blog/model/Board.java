package edu.kosmo.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
public class Board{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)// auto_increment 전략
	private int id;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Lob//대용량 데이터
	private String content;//섬머노트 라이브러리 - 우리가 적는 일반적인 글이 <html>태그가 섞여서 디자인되서 들어가. 
	
	
	
	private int count;//조회수
	
	@ManyToOne(fetch=FetchType.EAGER)//Board가 Many, User는 One - Board와 User는 n:1의 관계다. - FK처럼 연관관계를 만들어 주는 것. 
	@JoinColumn(name="userId")
	private User user;//DB는 오브젝트를 저장할 수 없어. 자바의 경우 오브젝트를 저장할 수 있어. 
							  //자바프로그램에서 DB에 맞춰서 테이블을 만들게 되. -  FK를 사용해 연결.
	
	//Board를 select 시 Reply도 불러와야 하므로 필요함
	@OneToMany(mappedBy = "board", fetch=FetchType.EAGER)//@JoinColumn(name="reply")는 필요가 없어. Board는 pk이므로 fk컬럼을 만들지 않으므로
	private List<Reply> reply;
	
	@CreationTimestamp//데이터가 인설트 될 때, 업데이트 될 때 자동으로 값이 들어가
	private Timestamp createDate;
	

	
	
}
