package edu.kosmo.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.kosmo.blog.model.User;
import lombok.Getter;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행학 완료가 되면 UserDetails 타입의 오브젝트를
//스프링 시큐리티의 고유한 세션 저장소에 저장을 해 준다. 그 때 저장되는 게 UserDtails타입의 PrincipalDetail(현재 클래스)
@Getter	 
public class PrincipalDetail implements UserDetails{
	private User user;//컴포지션 - 객체를 품고 있는 것.


	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public String getPassword() { return user.getPassword();}

	@Override
	public String getUsername() { return user.getUsername();}
	
	//계정이 만료되지 않았는지 리턴(true:만료 안 됨)
	@Override
	public boolean isAccountNonExpired() { return true;}
	
	//계정이 잠겨있지 않았는지 리턴한다.(true: 잠기지 않음) 
	@Override
	public boolean isAccountNonLocked() { return true;}
	
	//비밀번호가 만료되지 않았는지 리턴한다.(true: 만료 안 됨)
	@Override
	public boolean isCredentialsNonExpired() { return true;}
	
	//계정이 활성화(사용가능)인지 리턴한다.(true: 활성화)
	@Override
	public boolean isEnabled() { return true ;}
	
	//계정의 권한을 리턴. 타입이 컬렉션, GrantedAuthority를 상속해야.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//ArrayList는 컬렉션 타입
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(()->{return "ROLE_" + user.getRole();});
		return collectors;
	}
	/*
	  @Override public Collection<? extends GrantedAuthority> getAuthorities() {
	  //ArrayList는 컬렉션 타입 Collection<GrantedAuthority> collectors = new
	  ArrayList<>(); collectors.add(new GrantedAuthority() {
	   //자바는 메서드를 add에 못 넣지만 객체는 넣을 수 있어. 
	   //그런데 GrantedAuthority()는 메서드가 getAuthority()하나야 
	   //굳이 메서드를 한 번 감싸야 해. 그래서 자바 1.8부터는 람다식을 지
	  
	  @Override public String getAuthority() {//new GrantedAuthority() 할 때 오버라이딩.
	  return "ROLE_" + user.getRole();
	  //스프링에서 롤을 받을 때 규칙. ROLE_USER. 
	  //그냥 ROLE이라고 하면 인식을 못해. 
	  } }); return collectors; }
	 */
}
