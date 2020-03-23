package ru.gds.spring.dto;

import ru.gds.spring.domain.Status;

public class StatusDto {

    private long id;
    private String name;
    private long selected;

    public StatusDto(long id, String name, long selected){
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSelected() {
        return selected;
    }

    public void setSelected(long selected) {
        this.selected = selected;
    }

    public static StatusDto toDto(Status status) {
        return new StatusDto(
                status.getId(),
                status.getName(),
                0
        );
    }
}
