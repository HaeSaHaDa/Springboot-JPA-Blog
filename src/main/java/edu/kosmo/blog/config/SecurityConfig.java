package edu.kosmo.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.kosmo.blog.config.auth.PrincipalDetailService;
//아래 어노테이션은 스프링 시큐리티 세트
@Configuration//빈 등록 관리(IoC 관리)
@EnableWebSecurity // 시큐리티 필터에 등록 => 스프링  시큐리티가 활성화가 되어 있는데, 어떤 설정을 해당 파일에서 하겠다.
@EnableGlobalMethodSecurity(prePostEnabled=true)//특정 주소로 접근을 하면 권한및 인증을 미리 체크하겠다는 뜻.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	//해쉬 암호화. - 시큐리티가 들고 있는 함수.
	@Bean //IoC가 됨 => 이 함수가 리턴하는 new BCryptPasswordEncoder() 를 스프링이 관리함. 그래서 필요할 때마다 가져와서 쓰면 됨.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	//시큐리티가 대신 로그인 할 때 password를 가로채기를 하는데 
	//해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야.
	//입력받은 password를 해쉬로 암호화 해서 DB에 있는 해쉬랑 비교하는 메서드.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD()); 
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable() //csrf 토큰 비활성화(테스트시 걸어두는 게 좋음.)
			.authorizeRequests() 	//요청이 들어오면
				.antMatchers("/","/auth/**","/js/**","/css/**", "/image/**") // /, /auth, /js, /css, /image로 들어오는 모든 접근은
				.permitAll() //모두 허용.
				.anyRequest() //그외의 모든 요청은
				.authenticated() //권한을 얻어야 해.
			.and() 
				.formLogin()
				.loginPage("/auth/loginForm") //인증이 필요한 곳으로 접근하면 이동할 페이지
				.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 요청오면 로그인을 가로채서 대신 로그인을 해 => 가로채서 로그인 할 때 만들어야 할 클래스가 있어. UserDetails타입의 User클래스 
				.defaultSuccessUrl("/"); //로그인이 정상적으로 완료가 되면 /로 이동. =>.failureUrl("")은 로그인 실패시 이동하는 url
	}
	
}
