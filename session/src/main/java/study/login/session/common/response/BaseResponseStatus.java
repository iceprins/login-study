package study.login.session.common.response;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    SUCCESS_LOGIN(true, 1000, "로그인에 성공했습니다."),
    SUCCESS_SIGNUP(true, 1001, "회원가입에 성공했습니다."),
    SUCCESS_LOGOUT(true, 1002, "로그아웃 되었습니다."),
    SUCCESS_GET_LOGIN_USER(true, 200, "로그인 된 사용자입니다."),
    UNAUTHORIZED_MEMBER(false, 2001, "가입되지 않은 이메일입니다."),
    NOT_FOUND_MEMBER(false, 2002, "일치하는 사용자가 없습니다."),
    CONFLICT_MEMBER(false, 2003, "이미 사용 중인 이메일입니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
