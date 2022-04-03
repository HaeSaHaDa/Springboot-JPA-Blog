package edu.kosmo.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.kosmo.blog.model.User;



public interface UserRepository extends JpaRepository<User, Integer>{
	//SELECT * FROM user WHERE username=?;
	Optional<User> findByUsername(String username);
}




//Spring Jpa의 naming 전략
//JPA Naming 쿼리
//JpaRepository에는 원래 없어. 
//이 이름의 함수를 입력하면 다음의 쿼리가 자동으로 실행되.
//SELECT*FROM user WHERE username=? AND password=?; 
//User findByUsernameAndPassword(String username, String password);

//위 함수와 똑같은 함수.
//@Query(value="SELECT*FROM user WHERE username=? AND password=?", nativeQuery = true)
//User login(String username, String password);