package com.fairfree.reactive.service.impl;

import com.fairfree.reactive.model.User;
import com.fairfree.reactive.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void it_Should_Save_And_Return_Saved_User() {
        User user = new User(null, "Badri Paudel", "badri@gmail.com");
        User savedUser = new User(1L, "Badri Paudel", "badri@gmail.com");

        when(userRepository.save(user)).thenReturn(Mono.just(savedUser));

        StepVerifier.create(userService.saveUser(user))
                .expectNext(savedUser)
                .verifyComplete();
    }

    @Test
    void it_Should_Return_Valid_User_When_Searched_By_Email() {
        String email = "badri@gmail.com";
        User userByEmail = new User(1L, "Badri Paudel", "badri@gmail.com");
        when(userRepository.findByEmail(email)).thenReturn(Mono.just(userByEmail));

        StepVerifier.create(userService.findByEmail(email))
                .expectNext(userByEmail)
                .verifyComplete();
    }

    @Test
    void it_Should_Return_Empty_When_Email_Not_Found() {
        String email = "notfound@gmail.com";

        when(userRepository.findByEmail(email))
                .thenReturn(Mono.empty());

        StepVerifier.create(userService.findByEmail(email))
                .verifyComplete();
    }

}