package study.login.session.common.response;

public class BaseResponseService {

    public static <T> BaseResponse<T> success(BaseResponseStatus status, T data) {
        return BaseResponse.<T>builder()
                .isSuccess(status.isSuccess())
                .message(status.getMessage())
                .code(status.getCode())
                .data(data)
                .build();
    }

    public static BaseResponse<Object> success(BaseResponseStatus status) {
        return success(status, null);
    }
}
