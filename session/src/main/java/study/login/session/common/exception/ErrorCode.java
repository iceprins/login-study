package study.login.session.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    CONFLICT_MEMBER(409, "이미 사용 중인 이메일입니다."),
    UNAUTHORIZED_MEMBER(401, "로그인 정보가 잘못되었습니다."),
    ENCRYPTION_ERROR(500, "암호화 처리 중 에러가 발생했습니다."),
    INTERNAL_ERROR(500, "서버에서 에러가 발생했습니다.");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
