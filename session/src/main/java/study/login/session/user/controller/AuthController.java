package study.login.session.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.login.session.user.dto.request.UserLoginRequest;
import study.login.session.user.dto.response.UserLoginResponse;
import study.login.session.user.dto.request.UserSignupRequest;
import study.login.session.common.response.BaseResponse;
import study.login.session.common.response.BaseResponseService;
import study.login.session.common.response.BaseResponseStatus;
import study.login.session.user.service.AuthService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public BaseResponse<Object> signup(@RequestBody UserSignupRequest request) {
        authService.register(request);
        return BaseResponseService.success(BaseResponseStatus.SUCCESS_SIGNUP);
    }

    @PostMapping("/login")
    public BaseResponse<Object> login(@RequestBody UserLoginRequest request, HttpSession session) {
        UserLoginResponse res = authService.login(request, session);
        return BaseResponseService.success(BaseResponseStatus.SUCCESS_LOGIN, res);
    }

    @PostMapping("/logout")
    public BaseResponse<Object> logout(HttpSession session) {
        authService.logout(session);
        return BaseResponseService.success(BaseResponseStatus.SUCCESS_LOGOUT);
    }

    @GetMapping("/me")
    public BaseResponse<Object> getCurrentUser(HttpSession session) {
        UserLoginResponse currentUser = authService.getCurrentUser();
        return BaseResponseService.success(BaseResponseStatus.SUCCESS_GET_LOGIN_USER, currentUser);
    }
}
