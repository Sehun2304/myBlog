package com.sparta.myblog.controller;


import com.sparta.myblog.dto.ApiResult;
import com.sparta.myblog.dto.LoginRequestDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.sparta.myblog.dto.SignupRequestDto;
import com.sparta.myblog.entity.User;
import com.sparta.myblog.service.UserService;
import javax.validation.Valid;



@Validated //AOP 기반으로 메소드의 요청을 가로채서 유효성 검증을 진행해준다
@RestController //@Controller에 @ResponseBody가 결합된 어노테이션, RestController를 붙이면, 컨트롤러 클래스 하위 메서드에 @ResponseBody 어노테이션을 붙이지 않아도 문자열과 JSON 등을 전송할 수 있다
@RequiredArgsConstructor //새로운 필드를 추가할 때 다시 생성자를 만들어서 관리해야하는 번거로움을 없애준다.
@RequestMapping("api/auth")

public class UserController {

    private final UserService userService;

    // 회원가입 API
    @PostMapping("/signup")
    public ApiResult signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        User user = userService.signup(signupRequestDto);
        return new ApiResult("회원가입 성공", HttpStatus.OK.value()); // 회원가입 성공시 ApiResult Dto를 사용하여 성공메세지와 statusCode를 띄움
    }
    // 로그인 API
    @ResponseBody
    @PostMapping("/login")
    public ApiResult login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String token = userService.login(loginRequestDto, response);
        return new ApiResult("로그인 성공", HttpStatus.OK.value()); // 로그인 성공시 ApiResult Dto를 사용하여 성공메세지와 statusCode를 띄움
    }

}
