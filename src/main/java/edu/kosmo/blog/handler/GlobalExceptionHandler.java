package edu.kosmo.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import edu.kosmo.blog.dto.ResponseDto;

//어디에서든 exception가 발생하든 이 클래스로 오게 하기 위해서 @ControllerAdvice 을 달아 줘야.
@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
	}

}
