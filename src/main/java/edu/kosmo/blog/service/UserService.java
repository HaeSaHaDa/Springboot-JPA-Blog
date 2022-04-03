package edu.kosmo.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kosmo.blog.model.RoleType;
import edu.kosmo.blog.model.User;
import edu.kosmo.blog.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional(readOnly = true)
	public User 회원찾기(String username) {
		//못 찾으면 빈 객체 리턴
		User user = userRepository.findByUsername(username).orElseGet(()->{
			return new User();
		});
		return user;
	}
	
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();//1234원래 pw
		String encPassword= encoder.encode(rawPassword);//해쉬화 pw
		System.out.println("해쉬화 된 비밀번호: " + encPassword);
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		
		userRepository.save(user);
	}
	
	@Transactional
	public void 회원수정(User user) {
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		//select를 해서 User오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!!
		//영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줘
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		persistance.setEmail(user.getEmail());
				
		//회원수정 함수 종료시 = 서비스가 종료. = 트랜잭션이 종료 = commit이 자동으로 됩니다.
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.
	}
	@Transactional
	public void 회원비밀번호수정(User user) {
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		//select를 해서 User오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서!!
		//영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줘
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		//Validate 체크 => oauth 필드에 값이 없으면 수정 가능
		if(persistance.getOauth()==null ||persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			if(rawPassword.length() != 0) {
				String encPassword = encoder.encode(rawPassword);
				persistance.setPassword(encPassword);
			}
			
		}
						
		//회원수정 함수 종료시 = 서비스가 종료. = 트랜잭션이 종료 = commit이 자동으로 됩니다.
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.
	}
	
}
