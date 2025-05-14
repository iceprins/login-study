package study.login.session.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.login.session.domain.Users;
import study.login.session.domain.dto.UserLoginRequest;
import study.login.session.domain.dto.UserLoginResponse;
import study.login.session.domain.dto.UserSignupRequest;
import study.login.session.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public void register(UserSignupRequest request) {
        Users users = Users.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .name(request.getName())
                .build();
        userRepository.save(users);
    }

    public UserLoginResponse login(UserLoginRequest request, HttpSession session) {
        return userRepository.findUsersByEmail(request.getEmail())
                .filter(users -> users.getPassword().equals(request.getPassword()))
                .map(users -> {
                    session.setAttribute("userId", users.getId());
                    return new UserLoginResponse(users.getName(), users.getEmail());
                })
                .orElse(null);
    }

    public void logout(HttpSession session) {
        session.invalidate();
    }

    public boolean isDuplicated(String email) {
        return userRepository.findUsersByEmail(email).isPresent();
    }
}
