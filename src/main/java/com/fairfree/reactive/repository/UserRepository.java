package com.fairfree.reactive.repository;

import com.fairfree.reactive.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    // Mono as it only returns one user
    Mono<User> findByEmail(String email);
}
