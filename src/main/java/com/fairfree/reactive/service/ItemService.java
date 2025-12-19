package com.fairfree.reactive.service;

import com.fairfree.reactive.model.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemService {
    Mono<Item> createItem(Item item);

    Flux<Item> getAllItems(int page, int size);

    Flux<Item> getItemsByOwner(Long userId);
}
