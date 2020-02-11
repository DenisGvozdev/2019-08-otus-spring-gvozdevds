package ru.gds.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.StatusRepository;
import ru.gds.spring.interfaces.StatusRepositoryCustom;

@Transactional
@Repository
public class StatusRepositoryImpl implements StatusRepositoryCustom {

    private StatusRepository statusRepository;

    StatusRepositoryImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status findById(long id) {
        return statusRepository.findById(id);
    }
}
