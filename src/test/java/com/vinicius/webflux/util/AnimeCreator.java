package com.vinicius.webflux.util;

import com.vinicius.webflux.domain.model.Anime;

public class AnimeCreator {

    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Tenseu Shitara")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .id(1L)
                .name("Tenseu Shitara")
                .build();
    }

    public static Anime createValidUpdateAnime() {
        return Anime.builder()
                .id(1L)
                .name("Tenseu Shitara 2")
                .build();
    }
}
