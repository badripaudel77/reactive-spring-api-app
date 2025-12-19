package com.fairfree.reactive.service.impl;

import com.fairfree.reactive.model.User;
import com.fairfree.reactive.repository.UserRepository;
import com.fairfree.reactive.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public Mono<@NonNull User> saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
