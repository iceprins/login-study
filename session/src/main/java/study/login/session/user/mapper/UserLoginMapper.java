package study.login.session.user.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.login.session.user.domain.Users;
import study.login.session.user.dto.response.UserLoginResponse;
import study.login.session.user.crypto.EncryptionService;

@Component
@RequiredArgsConstructor
public class UserLoginMapper {
    private final EncryptionService encryptionService;

    public UserLoginResponse toResponse(Users user) {
        return new UserLoginResponse(
                encryptionService.decrypt(user.getName()),
                encryptionService.decrypt(user.getEmail())
        );
    }
}
