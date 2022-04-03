package edu.kosmo.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.kosmo.blog.model.RoleType;
import edu.kosmo.blog.model.User;
import edu.kosmo.blog.repository.UserRepository;

@RestController//회원가입이 잘 됐다는 메시지만 줄 수 있게 RestController로 만듦
public class DummyControllerTest {

	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		return "삭제 되었습니다 - id: " + id;
	}
	
	@Autowired
	private UserRepository userRepository;
	
	//email, password 수정. - 수정할 때 id랑 username은 수정하지 못하게 할 거야. 
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id: " + id);
		System.out.println("password: " + requestUser.getPassword());
		System.out.println("email: " + requestUser.getEmail());
		//update를 하려면 먼저 유저를 먼저 찾아야 해.(자바 1.8부터 람다식을 이용해 매개변수자리에 함수를 넣을 수 있음.)
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		//user는 찾아온 거기 때문에 null이 없어.
		//userRepository.save(user);
		return user;
	}
	
	//여러 건의 데이터 리턴
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	//한 페이지당 2건의 데이터를 리턴 받아볼 예정
	//2건씩 들고오고 sort는 id로 하고 id는 최신 순으로 해서 가져올 거야. 
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		//findAll(pageable) 메서드는 page를 리턴.
		Page<User> pagingUser = userRepository.findAll(pageable);//.getContent()를 붙이면 List타입으로 리턴해줘. 

		List<User> users = pagingUser.getContent();
		return users;
	}
	
	//select
	@GetMapping("/dummy/user/{id}")//restful 방식, 주소로 파라메터를 전달 받을 수 있음.
	public User detail(@PathVariable int id) {
		//findById(id)는 Optional로 return해서 user를 return값으로 못 써.
		// user/4를 찾으면 내가 DB에서 못 찾으면 user가 null이 될 거 아님? 그럼 return null이 되니까 문제가 되지 않겠니??
		//그래서 Optional로 너의 Uesr객체를 감싸서 가져올 테니 null인지 아닌지 판단해서 return해.
		//user로 리턴하는 방법 1. userRepository.findById(id).get() - null이 리턴될 일 없으니 user 객체를 뽑아서 리턴해.
		//orElseThrow 활용
		//람다식 활용(자바1.8) - 익명함수 활용 - 리턴 타입을 전혀 몰라도 됨.
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저가 없습니다. id:" + id);
			}
		});
		return user;
	}
	
	//회원가입을 위해 PostMapping
	//http://localhost:8282/blog/dummy/join (요청)
	//http의 body에 username, password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("유저 이름: " +  user.getUsername());
		System.out.println("비밀 번호: " +  user.getPassword());
		System.out.println("유저이메일: " +  user.getEmail());
		System.out.println("권한: " + user.getRole());
		System.out.println("createDate" + user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
	
}
