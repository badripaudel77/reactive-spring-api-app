package com.fairfree.reactive.controller;

import com.fairfree.reactive.model.Item;
import com.fairfree.reactive.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
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
}

