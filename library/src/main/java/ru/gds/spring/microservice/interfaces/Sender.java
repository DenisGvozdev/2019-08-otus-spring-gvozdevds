package ru.gds.spring.microservice.interfaces;

import ru.gds.spring.microservice.params.ParamsBookContent;

public interface Sender {

    String get(String uri);

    void post(String uri, String json);

    void postMVC(String uri, ParamsBookContent params);

    void put(String uri, String json);

    void delete(String uri);
}
