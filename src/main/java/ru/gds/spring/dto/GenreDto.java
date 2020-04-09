package ru.gds.spring.dto;

import ru.gds.spring.domain.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreDto {

    private String id;
    private String name;
    private boolean selected;

    private GenreDto() {}

    private GenreDto(String id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static GenreDto toDtoLight(Genre genre) {
        if (genre == null)
            return new GenreDto();

        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        return genreDto;
    }

    public static GenreDto toDtoWithSelect(Genre genre, List<Genre> genreList) {
        if (genre == null || genreList == null)
            return new GenreDto();

        List<String> genreIds = new ArrayList<>();
        genreList.forEach((e) -> genreIds.add(e.getId()));

        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        genreDto.setSelected(genreIds.contains(genre.getId()));
        return genreDto;
    }

    static GenreDto toDto(Genre genre) {
        if (genre == null)
            return new GenreDto();

        return new GenreDto(
                genre.getId(),
                genre.getName(),
                false
        );
    }
}
