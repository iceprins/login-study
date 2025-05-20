package study.login.session.global.exception;

public class EncryptionException extends BaseException {

    public EncryptionException() {
        super(ErrorCode.ENCRYPTION_ERROR);
    }
}
