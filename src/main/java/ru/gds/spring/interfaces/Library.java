package ru.gds.spring.interfaces;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.gds.spring.mongo.dto.BookContentDto;

import java.util.List;

@MessagingGateway
public interface Library {

    @Gateway(requestChannel = "inputChannel", replyChannel = "outputChannel")
    List<BookContentDto> process(List<String> orders);
}
