package com.liujianan.collab.user;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {
    private final ConcurrentHashMap<Long, UserAccount> users = new ConcurrentHashMap<>();

    public UserRepository() {
        LocalDateTime now = LocalDateTime.now();
        save(new UserAccount(1L, "admin", "admin123", "刘家楠", Role.ADMIN, true, now));
        save(new UserAccount(2L, "manager", "manager123", "项目负责人", Role.MANAGER, true, now));
        save(new UserAccount(3L, "member", "member123", "开发成员", Role.MEMBER, true, now));
    }

    public List<UserAccount> findAll() {
        return users.values().stream().sorted((a, b) -> a.id().compareTo(b.id())).toList();
    }

    public Optional<UserAccount> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<UserAccount> findByUsername(String username) {
        return users.values().stream().filter(user -> user.username().equals(username)).findFirst();
    }

    public UserAccount save(UserAccount user) {
        users.put(user.id(), user);
        return user;
    }
}
