package com.fairfree.reactive.service.impl;

import com.fairfree.reactive.model.Item;
import com.fairfree.reactive.repository.ItemRepository;
import com.fairfree.reactive.service.ItemService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository repository;

    public ItemServiceImpl(ItemRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Item> createItem(Item item) {
        return this.repository.save(item);
    }

    @Override
    public Flux<Item> getAllItems(int size, int offset) {
        return this.repository.findAllPaged(size, offset);
    }

    @Override
    public Flux<Item> getItemsByOwner(Long userId) {
        return this.repository.findByOwnerId(userId);
    }

}
