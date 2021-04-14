package com.vinicius.webflux.domain.repository;

import com.vinicius.webflux.domain.model.Anime;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface AnimeRepository extends ReactiveCrudRepository<Anime, Long> {

    Mono<Anime> findById(Long id);
}
