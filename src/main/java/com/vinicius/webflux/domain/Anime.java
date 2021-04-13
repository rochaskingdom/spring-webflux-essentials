package com.vinicius.webflux.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@With
@Table("anime")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Anime {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @NotNull
    @NotEmpty(message = "The name of this anime cannot be empty")
    private String name;
}
