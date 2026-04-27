package com.liujianan.collab.auth;

import com.liujianan.collab.security.UserPrincipal;

public record LoginResponse(String token, UserPrincipal user) {
}
