package study.login.session.user.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionManager {
    private static final String USER_ID_KEY = "userId";

    public Long getUserId() {
        HttpServletRequest request = getCurrentRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (Long) session.getAttribute(USER_ID_KEY);
    }

    public void storeUserId(HttpSession session, Long userId) {
        session.setAttribute("userId", userId);
    }

    public Long getUserId(HttpSession session) {
        return (Long) session.getAttribute("userId");
    }

    public void invalidate(HttpSession session) {
        session.invalidate();
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
    }
}
