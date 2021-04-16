package com.vinicius.webflux.controller;

import com.vinicius.webflux.domain.model.Anime;
import com.vinicius.webflux.domain.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/animes")
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping
    public Flux<Anime> listAll() {
        return animeService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Anime> findById(@PathVariable Long id) {
        return animeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Anime> save(@Valid @RequestBody Anime anime) {
        return animeService.save(anime);
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<Anime> saveBatch(@RequestBody List<Anime> animes) {
        return animeService.saveAll(animes);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> update(@PathVariable Long id, @Valid @RequestBody Anime anime) {
        return animeService.update(anime.withId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return animeService.delete(id);
    }

}
