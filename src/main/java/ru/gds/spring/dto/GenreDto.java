package ru.gds.spring.dto;

import ru.gds.spring.domain.Genre;

public class GenreDto {

    private long id;
    private String name;
    private long selected;

    public GenreDto(long id, String name, long selected){
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

    public long getSelected() {
        return selected;
    }

    public void setSelected(long selected) {
        this.selected = selected;
    }

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(
                genre.getId(),
                genre.getName(),
                0
        );
    }
}
