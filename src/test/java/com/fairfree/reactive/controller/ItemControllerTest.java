package com.fairfree.reactive.controller;

import com.fairfree.reactive.model.Item;
import com.fairfree.reactive.service.ItemService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest(ItemController.class)
class ItemControllerTest {

    @Autowired
    private WebTestClient client;

    @MockitoBean
    private ItemService itemService;

    @Test
    void createItem() {
        Item itemRequest = new Item(null, "Jacket", "Winter One", 2, 5L);
        Item itemResponse = new Item(1L, "Jacket", "Winter One", 2, 5L);

        Mockito.when(itemService.createItem(itemRequest))
                .thenReturn(Mono.just(itemResponse));

        client.post()
                .uri("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(itemRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("Jacket")
                .jsonPath("$.description").isEqualTo("Winter One")
                .jsonPath("$.quantity").isEqualTo(2)
                .jsonPath("$.ownerId").isEqualTo(5);
    }

    @Test
    void getAllItems() {
        Item item1 = new Item(1L, "Item 1", "Desc 1", 5, 1L);
        Item item2 = new Item(2L, "Item 2", "Desc 2", 10, 1L);

        int page = 0;
        int size = 2;

        Mockito.when(itemService.getAllItems(page, size))
                .thenReturn(Flux.just(item1, item2));

        client.get()
                .uri(
                        uriBuilder -> uriBuilder.path("/api/items")
                                        .queryParam("page", page)
                                        .queryParam("offset", size)
                                        .build()
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo("Item 1")
                .jsonPath("$[1].id").isEqualTo(2)
                .jsonPath("$[1].name").isEqualTo("Item 2");
    }

    @Test
    void getItemsByOwner() {
        Long ownerId = 1L;
        Item item1 = new Item(1L, "Item 1", "Desc 1", 5, ownerId);
        Item item2 = new Item(2L, "Item 2", "Desc 2", 10, ownerId);

        Mockito.when(itemService.getItemsByOwner(ownerId))
                .thenReturn(Flux.just(item1, item2));

        client.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/api/items/owner/{ownerId}")
                                .build(ownerId)
                )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo(1)
                .jsonPath("$[0].name").isEqualTo("Item 1")
                .jsonPath("$[0].ownerId").isEqualTo(1)
                .jsonPath("$[1].id").isEqualTo(2)
                .jsonPath("$[1].name").isEqualTo("Item 2")
                .jsonPath("$[1].ownerId").isEqualTo(1);
    }

}