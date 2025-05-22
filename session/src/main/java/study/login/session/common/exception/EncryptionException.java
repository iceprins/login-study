package study.login.session.common.exception;

public class EncryptionException extends BaseException {

    public EncryptionException() {
        super(ErrorCode.ENCRYPTION_ERROR);
    }
}
