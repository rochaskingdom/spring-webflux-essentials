package com.vinicius.webflux.domain.service;

import com.vinicius.webflux.domain.model.Anime;
import com.vinicius.webflux.domain.repository.AnimeRepository;
import io.netty.util.internal.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    public Flux<Anime> findAll() {
        return animeRepository.findAll();
    }

    public Mono<Anime> findById(Long id) {
        return animeRepository.findById(id)
                .switchIfEmpty(monoResponseStatusNotFoundException());
    }

    public <T> Mono<T> monoResponseStatusNotFoundException() {
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Anime not found"));
    }

    public Mono<Anime> save(Anime anime) {
        return animeRepository.save(anime);
    }

    @Transactional
    public Flux<Anime> saveAll(List<Anime> animes) {
        return animeRepository.saveAll(animes)
                .doOnNext(this::throwResponseStatusExceptionWhenEmptyName);
    }

    private void throwResponseStatusExceptionWhenEmptyName(Anime anime) {
        if (StringUtil.isNullOrEmpty(anime.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Name");
        }
    }

    public Mono<Void> update(Anime anime) {
        return findById(anime.getId())
                .map(animeFound -> anime.withId(animeFound.getId()))
                .flatMap(animeRepository::save)
                .then();
    }

    public Mono<Void> delete(Long id) {
        return findById(id)
                .flatMap(animeRepository::delete);
    }
}
