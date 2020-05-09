package ru.gds.spring.microservice.params;

public class ParamsGenre {

    private String id;
    private String name;

    private ParamsGenre() {
    }

    private ParamsGenre(String id, String name) {
        this.id = id;
        this.name = name;
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
}
