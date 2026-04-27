package com.liujianan.collab.security;

import com.liujianan.collab.common.BusinessException;
import com.liujianan.collab.user.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Service
public class JwtService {
    private final String secret;
    private final long expireMinutes;

    public JwtService(@Value("${app.jwt.secret}") String secret,
                      @Value("${app.jwt.expire-minutes}") long expireMinutes) {
        this.secret = secret;
        this.expireMinutes = expireMinutes;
    }

    public String issue(UserPrincipal principal) {
        long expireAt = Instant.now().plusSeconds(expireMinutes * 60).getEpochSecond();
        String header = base64("{\"alg\":\"HS256\",\"typ\":\"JWT\"}");
        String payload = base64(String.join("|",
                principal.id().toString(),
                principal.username(),
                principal.displayName(),
                principal.role().name(),
                Long.toString(expireAt)
        ));
        String signature = sign(header + "." + payload);
        return header + "." + payload + "." + signature;
    }

    public UserPrincipal parse(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new SecurityException("invalid token");
        }
        String expected = sign(parts[0] + "." + parts[1]);
        if (!expected.equals(parts[2])) {
            throw new SecurityException("invalid token signature");
        }
        String payload = new String(Base64.getUrlDecoder().decode(parts[1]), StandardCharsets.UTF_8);
        String[] fields = payload.split("\\|");
        if (fields.length != 5) {
            throw new SecurityException("invalid token payload");
        }
        long expireAt = Long.parseLong(fields[4]);
        if (Instant.now().getEpochSecond() > expireAt) {
            throw new SecurityException("token expired");
        }
        return new UserPrincipal(Long.parseLong(fields[0]), fields[1], fields[2], Role.valueOf(fields[3]));
    }

    private String base64(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String sign(String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(mac.doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception exception) {
            throw new BusinessException("failed to sign token");
        }
    }
}
