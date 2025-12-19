package com.fairfree.reactive.controller;

import com.fairfree.reactive.model.User;
import com.fairfree.reactive.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(UserController.class)
class UserControllerTest {

    @Autowired
    private WebTestClient client;

    @MockitoBean
    private UserService userService;

    @Test
    void should_Create_User() {
        User request = new User(null, "Badri Paudel", "badri@gmail.com");
        User response = new User(1L, "Badri Paudel", "badri@gmail.com");

        Mockito.when(userService.saveUser(request))
                .thenReturn(Mono.just(response));

        client.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Badri Paudel")
                .jsonPath("$.email").isEqualTo("badri@gmail.com");
    }


    @Test
    void should_Find_User_By_Email() {
        User user = new User(1L, "Badri Paudel", "badri@gmail.com");

        Mockito.when(userService.findByEmail("badri@gmail.com"))
                .thenReturn(Mono.just(user));

        client.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/api/users/{userEmail}")
                                .build("badri@gmail.com"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Badri Paudel")
                .jsonPath("$.email").isEqualTo("badri@gmail.com");
    }
}