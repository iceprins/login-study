package study.login.session.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import study.login.session.domain.dto.UserLoginRequest;
import study.login.session.domain.dto.UserLoginResponse;
import study.login.session.domain.dto.UserSignupRequest;
import study.login.session.global.exception.BaseException;
import study.login.session.global.exception.ErrorCode;
import study.login.session.global.response.BaseResponse;
import study.login.session.global.response.BaseResponseService;
import study.login.session.global.response.BaseResponseStatus;
import study.login.session.service.AuthService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public BaseResponse<Object> signup(@RequestBody UserSignupRequest request) {
        if (authService.isDuplicated(request.getEmail())) {
            throw new BaseException(ErrorCode.CONFLICT_MEMBER);
        }
        authService.register(request);
        return BaseResponseService.success(BaseResponseStatus.SUCCESS_SIGNUP);
    }

    @PostMapping("/login")
    public BaseResponse<Object> login(@RequestBody UserLoginRequest request, HttpSession session) {
        UserLoginResponse res = authService.login(request, session);
        if (res != null) {
            return BaseResponseService.success(BaseResponseStatus.SUCCESS_LOGIN, res);
        }
        throw new BaseException(ErrorCode.UNAUTHORIZED_MEMBER);
    }

    @PostMapping("/logout")
    public BaseResponse<Object> logout(HttpSession session) {
        authService.logout(session);
        return BaseResponseService.success(BaseResponseStatus.SUCCESS_LOGOUT);
    }

    @GetMapping("/me")
    public BaseResponse<Object> getCurrentUser(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            throw new BaseException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
        return BaseResponseService.success(BaseResponseStatus.SUCCESS_GET_LOGIN_USER);
    }
}
