package com.fairfree.reactive.repository;

import com.fairfree.reactive.model.Item;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ItemRepository extends ReactiveCrudRepository<Item, Long> {
    // Flux because it may return multiple items for given owner
    Flux<Item> findByOwnerId(Long ownerId);

    @Query("""
            SELECT * FROM items
            ORDER BY id
            LIMIT :size OFFSET :offset
    """)
    Flux<Item> findAllPaged(@Param("size") int size, @Param("offset") int offset);
}
