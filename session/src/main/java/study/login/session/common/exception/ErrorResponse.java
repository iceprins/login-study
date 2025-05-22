package study.login.session.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {
    private int status;
    private ErrorCode code;
    private String message;

    public ErrorResponse(ErrorCode code) {
        this.status = code.getCode();
        this.code = code;
        this.message = code.getMessage();
    }
}
