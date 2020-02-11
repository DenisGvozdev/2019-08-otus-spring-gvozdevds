package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Status;

public interface StatusRepositoryCustom {

    Status findById(long id);
}
