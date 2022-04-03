package edu.kosmo.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.kosmo.blog.model.User;
import edu.kosmo.blog.repository.UserRepository;

@Service //Bean 등록
public class PrincipalDetailService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	//스프링이 로그인 요청을 가로챌 때, username,password변수 2개를 가로채는데 
	//password 부분 비교는 new PrincipalDetail을 리턴할 때, 
	//SecurityConfig.java에서 호출한
	//auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD()); 가 비밀번호 비교를 해줌.
	//우리는 username이 DB에 있는지만 확인해주면 됨. 확인은 이 함수에서 함. 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//findByUsername은 //SELECT * FROM user WHERE username=?; 실행 함수.
		//타입이 optional =>.orElseThrow()필요.
		User principal = userRepository.findByUsername(username) 
				.orElseThrow(()->{
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다. " + username);
				});
		return new PrincipalDetail(principal); //메서드 리턴 타입이 UserDetails. 그래서 PrincipalDetail()을 리턴하면 되.
													//=> PrincipalDetail()에 user
	}

}
