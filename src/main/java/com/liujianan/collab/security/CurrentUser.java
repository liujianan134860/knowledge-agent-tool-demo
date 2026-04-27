package com.liujianan.collab.security;

public final class CurrentUser {
    private static final ThreadLocal<UserPrincipal> HOLDER = new ThreadLocal<>();

    private CurrentUser() {
    }

    public static void set(UserPrincipal principal) {
        HOLDER.set(principal);
    }

    public static UserPrincipal get() {
        UserPrincipal principal = HOLDER.get();
        if (principal == null) {
            throw new SecurityException("unauthenticated");
        }
        return principal;
    }

    public static void clear() {
        HOLDER.remove();
    }
}
