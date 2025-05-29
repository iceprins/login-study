package study.login.session.user.crypto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.login.session.common.crypto.AesEncryptor;

@Component
@RequiredArgsConstructor
public class EncryptionService {
    private final AesEncryptor aesEncryptor;

    public String encrypt(String plain) {
        return aesEncryptor.encrypt(plain);
    }

    public String decrypt(String encrypted) {
        return aesEncryptor.decrypt(encrypted);
    }
}
