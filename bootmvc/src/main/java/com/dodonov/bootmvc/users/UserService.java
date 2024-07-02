package com.dodonov.bootmvc.users;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final AtomicLong idCounter;
    private final Map<Long, User> userMap;

    public UserService() {
        this.idCounter = new AtomicLong();
        this.userMap = new ConcurrentHashMap<>();
    }

    public User getUser(Long id) {
        if (!userMap.containsKey(id)) {
            throw new NoSuchElementException("No such user with id=%s".formatted(id));
        }
        return userMap.get(id);
    }

    public User createUser(User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("Id for user should not be provided");
        }
        if (user.getPets() != null && !user.getPets().isEmpty()) {
            throw new IllegalArgumentException("User pets must be empty");
        }
        Long id = idCounter.incrementAndGet();
        user.setId(id);
        user.setPets(new ArrayList<>());
        userMap.put(id, user);
        return user;
    }

    public void deleteUser(Long id) {
        userMap.remove(id);
    }

    public User updateUser(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("No user id passed");
        }
        if (!userMap.containsKey(user.getId())) {
            throw new NoSuchElementException("No such user with id=%s".formatted(user.getId()));
        }
        userMap.put(user.getId(), user);
        return user;
    }

    public List<User> getAllUsers() {
        return userMap.values().stream().toList();
    }
}
