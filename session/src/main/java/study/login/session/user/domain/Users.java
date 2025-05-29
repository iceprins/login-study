package study.login.session.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.login.session.common.exception.BaseException;
import study.login.session.common.exception.ErrorCode;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Builder
    private Users(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static Users create(String encryptedName, String encryptedEmail, String encryptedPassword) {
        return Users.builder()
                .name(encryptedName)
                .email(encryptedEmail)
                .password(encryptedPassword)
                .build();
    }

    public void validatePassword(String rawPassword, PasswordEncoder encoder) {
        if (!encoder.matches(rawPassword, this.password)) {
            throw new BaseException(ErrorCode.UNAUTHORIZED_MEMBER);
        }
    }
}
