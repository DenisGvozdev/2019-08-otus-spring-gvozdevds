package ru.gds.spring.dto;

import ru.gds.spring.domain.Status;

public class StatusDto {

    private long id;
    private String name;
    private boolean selected;

    private StatusDto() {
    }

    private StatusDto(long id, String name, boolean selected) {
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static StatusDto toDtoLight(Status status) {
        if (status == null)
            return null;

        StatusDto statusDto = new StatusDto();
        statusDto.setId(status.getId());
        statusDto.setName(status.getName());
        return statusDto;
    }

    public static StatusDto toDtoWithSelect(Status status, Long statusId) {
        if (status == null || statusId == null)
            return null;

        StatusDto statusDto = new StatusDto();
        statusDto.setId(status.getId());
        statusDto.setName(status.getName());
        statusDto.setSelected(status.getId() == statusId);
        return statusDto;
    }

    public static StatusDto toDto(Status status) {
        if (status == null)
            return null;

        return new StatusDto(
                status.getId(),
                status.getName(),
                false
        );
    }
}
