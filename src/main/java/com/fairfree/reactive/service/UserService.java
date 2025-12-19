package com.fairfree.reactive.service;

import com.fairfree.reactive.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> saveUser(User user);

    Flux<User> findAll();

    Mono<User> findByEmail(String email);
}
