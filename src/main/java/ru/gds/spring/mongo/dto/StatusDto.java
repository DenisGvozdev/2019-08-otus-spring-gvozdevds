package ru.gds.spring.mongo.dto;

import ru.gds.spring.mongo.domain.Status;

public class StatusDto {

    private String id;
    private String name;
    private boolean selected;

    public StatusDto() {
    }

    private StatusDto(String id, String name, boolean selected) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public static StatusDto toDtoWithSelect(Status status, String statusId) {
        if (status == null || statusId == null)
            return null;

        StatusDto statusDto = new StatusDto();
        statusDto.setId(status.getId());
        statusDto.setName(status.getName());
        statusDto.setSelected(statusId.equalsIgnoreCase(status.getId()));
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
