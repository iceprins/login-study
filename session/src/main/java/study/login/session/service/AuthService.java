package study.login.session.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.login.session.domain.Users;
import study.login.session.dto.request.UserLoginRequest;
import study.login.session.dto.response.UserLoginResponse;
import study.login.session.dto.request.UserSignupRequest;
import study.login.session.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EncryptionService encryptionService;

    public void register(UserSignupRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Users users = Users.builder()
                .email(encryptionService.encrypt(request.getEmail()))
                .password(encodedPassword)
                .name(encryptionService.encrypt(request.getName()))
                .build();
        userRepository.save(users);
    }

    public UserLoginResponse login(UserLoginRequest request, HttpSession session) {
        return userRepository.findUsersByEmail(encryptionService.encrypt(request.getEmail()))
                .filter(users -> passwordEncoder.matches(request.getPassword(), users.getPassword()))
                .map(users -> {
                    session.setAttribute("userId", users.getId());
                    return new UserLoginResponse(
                            encryptionService.decrypt(users.getName()), encryptionService.decrypt(users.getEmail())
                    );
                })
                .orElse(null);
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public boolean isDuplicated(String email) {
        return userRepository.findUsersByEmail(encryptionService.encrypt(email)).isPresent();
    }
}
