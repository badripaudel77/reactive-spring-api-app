package com.fairfree.reactive.controller;

import com.fairfree.reactive.model.Item;
import com.fairfree.reactive.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Item> createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @GetMapping("")
    public Flux<Item> getAllItems(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "5") int size) {
        return itemService.getAllItems(offset, size);
    }

    @GetMapping("/owner/{ownerId}")
    public Flux<Item> getItemsByOwner(@PathVariable Long ownerId) {
        return itemService.getItemsByOwner(ownerId);
    }

    @GetMapping(value = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Item> testItem() {
        return Flux.just(
                new Item(1L, "Jacket", "Winter One", 2, 5L),
                new Item(2L, "Coat", "Winter Two", 3, 5L),
                new Item(3L, "Hat", "Summer One", 1, 5L)
        ).delayElements(Duration.ofSeconds(2));
        // .collectList(); Use it for collecting data at once. Still reactive but sends data to client at once.
    }
}