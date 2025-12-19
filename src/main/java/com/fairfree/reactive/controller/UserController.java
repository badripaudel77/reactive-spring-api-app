package com.fairfree.reactive.controller;

import com.fairfree.reactive.model.User;
import com.fairfree.reactive.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public Flux<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{userEmail}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<User> findByEmail(@PathVariable("userEmail") String email) {
        return userService.findByEmail(email)
                .switchIfEmpty(Mono.error(new RuntimeException("User with email " + email + " not found.")));
    }
}
