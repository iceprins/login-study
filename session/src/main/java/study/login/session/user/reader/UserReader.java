package study.login.session.user.reader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.login.session.common.exception.BaseException;
import study.login.session.common.exception.ErrorCode;
import study.login.session.user.domain.Users;
import study.login.session.user.repository.UserRepository;
import study.login.session.user.crypto.EncryptionService;

@Component
@RequiredArgsConstructor
public class UserReader {
    private final UserRepository userRepository;
    private final EncryptionService encryptionService;

    public Users findByEmail(String plainEmail) {
        String encrypted = encryptionService.encrypt(plainEmail);
        return userRepository.findUsersByEmail(encrypted)
                .orElseThrow(() -> new BaseException(ErrorCode.UNAUTHORIZED_MEMBER));
    }
}
