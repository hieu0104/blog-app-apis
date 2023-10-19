package com.hieu.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hieu.blog.payloads.ApiResponse;

/*the class is handing exception  */

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHanlder(

			ResourceNotFoundException ex

	) {
		String message = ex.getMessage();
		ApiResponse apiResponse = new ApiResponse(message, false);

		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);

	}
	/*
	 * Phương thức handleMethodArgsNotValidException trả về một đối tượng
	 * ResponseEntity chứa một đối tượng Map chứa các thông tin lỗi.
	 * 
	 * Cụ thể, phương thức này sử dụng ex.getBindingResult().getAllErrors() để lấy
	 * danh sách các lỗi liên quan đến tham số không hợp lệ,
	 * 
	 * sau đó sử dụng một vòng lặp forEach để lấy tên trường và thông báo lỗi tương
	 * ứng từ các lỗi này.
	 * 
	 * Cuối cùng, phương thức trả về một đối tượng ResponseEntity với mã trạng thái
	 * HttpStatus.BAD_REQUEST và đối tượng Map chứa thông tin lỗi.
	 * 
	 * Phương thức này có thể được đăng ký với Spring Framework bằng cách sử dụng
	 * chú thích @ExceptionHandler trên một phương thức trong một @ControllerAdvice
	 * hoặc một @RestControllerAdvice class,
	 * 
	 * để xử lý các ngoại lệ của tất cả các phương thức trong các @Controller
	 * hoặc @RestController class tương ứng.
	 */
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(

			MethodArgumentNotValidException ex)

	{
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fileName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			resp.put(fileName, message);
		});
		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
	}
}
