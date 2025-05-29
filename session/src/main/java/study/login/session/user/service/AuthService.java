package study.login.session.user.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.login.session.common.exception.BaseException;
import study.login.session.common.exception.ErrorCode;
import study.login.session.user.domain.Users;
import study.login.session.user.dto.request.UserLoginRequest;
import study.login.session.user.dto.response.UserLoginResponse;
import study.login.session.user.dto.request.UserSignupRequest;
import study.login.session.user.crypto.EncryptionService;
import study.login.session.user.session.SessionManager;
import study.login.session.user.repository.UserRepository;
import study.login.session.user.mapper.UserLoginMapper;
import study.login.session.user.reader.UserReader;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserReader userReader;
    private final UserLoginMapper userLoginMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EncryptionService encryptionService;
    private final SessionManager sessionManager;

    public void register(UserSignupRequest request) {
        String encryptedName = encryptionService.encrypt(request.getName());
        String encryptedEmail = encryptionService.encrypt(request.getEmail());
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        if (userRepository.findUsersByEmail(encryptedEmail).isPresent()) {
            throw new BaseException(ErrorCode.CONFLICT_MEMBER);
        }

        Users user = Users.create(encryptedName, encryptedEmail, encryptedPassword);
        userRepository.save(user);
    }

    public UserLoginResponse login(UserLoginRequest request, HttpSession session) {
        Users user = userReader.findByEmail(request.getEmail());
        user.validatePassword(request.getPassword(), passwordEncoder);
        sessionManager.storeUserId(session, user.getId());
        return userLoginMapper.toResponse(user);
    }

    public void logout(HttpSession session) {
        sessionManager.invalidate(session);
    }

    public UserLoginResponse getCurrentUser() {
        Long userId = sessionManager.getUserId();
        if (userId == null) {
            throw new BaseException(ErrorCode.UNAUTHORIZED_MEMBER);
        }

        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNAUTHORIZED_MEMBER));

        return userLoginMapper.toResponse(user);
    }
}
