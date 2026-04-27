package com.liujianan.collab.auth;

import com.liujianan.collab.common.BusinessException;
import com.liujianan.collab.security.JwtService;
import com.liujianan.collab.security.UserPrincipal;
import com.liujianan.collab.user.UserAccount;
import com.liujianan.collab.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        UserAccount user = userRepository.findByUsername(request.username())
                .filter(candidate -> candidate.password().equals(request.password()))
                .filter(UserAccount::enabled)
                .orElseThrow(() -> new BusinessException("username or password is incorrect"));
        UserPrincipal principal = new UserPrincipal(user.id(), user.username(), user.displayName(), user.role());
        return new LoginResponse(jwtService.issue(principal), principal);
    }

    public List<String> demoAccounts() {
        return List.of("admin/admin123", "manager/manager123", "member/member123");
    }
}
