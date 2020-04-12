package ru.gds.spring.dto;

import ru.gds.spring.domain.Genre;

import java.util.List;

public class GenreDto {

    private long id;
    private String name;
    private boolean selected;

    private GenreDto() {
    }

    public GenreDto(long id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
            return null;

        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        return genreDto;
    }

    public static GenreDto toDtoWithSelect(Genre genre, List<Long> genreIds) {
        if (genre == null || genreIds == null)
            return null;

        GenreDto genreDto = new GenreDto();
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        genreDto.setSelected(genreIds.contains(genre.getId()));
        return genreDto;
    }

    static GenreDto toDto(Genre genre) {
        if (genre == null)
            return null;

        return new GenreDto(
                genre.getId(),
                genre.getName(),
                false
        );
    }
}
