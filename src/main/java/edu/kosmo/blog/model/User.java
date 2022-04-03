package edu.kosmo.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA는 ORM. ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술(변수명을 바꾸고 프로젝트를 실행하면 DB의 컬럼명도 바뀜)
@Entity//User클래스 변수를 읽어서 MySQL에 테이블이 생성이 된다.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User{

	@Id//Primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY)//프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.(시퀀스)
	private int id;//시퀀스(오라클), auto_incremet(MySQL) - 비워놔도 자동으로 입력됨
	
	//null값이 될 수 없음.
	@Column(nullable = false, length=100, unique=true)
	private String username;//아이디
	
	@Column(nullable = false, length=100)//123456=>해쉬(비밀번호 암호화)
	private String password;
		
	@Column(nullable = false, length=50)
	private String email;
	
	 //Enum을 쓰는 게 좋다. Enum을 쓰면 도메인을 정해줄 수 있어. 예를 들어 남, 녀 와 같이 범위를 정해 줄 수 있어. 
	//회원가입 했을 때 admin, user, manager 이런 권한을 주는 것 
	@Enumerated(EnumType.STRING)//DB에는 RoleType이 없으므로 필요한 어노테이션
	private RoleType role;//RoleType을 주면 RoleType에서 부여된 권한만 부여 됨.
	
	
	private String oauth;//kakao, google
	
	//회원이 가입했을 때 시간.
	@CreationTimestamp //시간이 자동 입력  - 비워놔도 자동으로 입력됨
	private Timestamp createDate;
	
}
