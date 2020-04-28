package ru.gds.spring.interfaces;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.gds.spring.config.Constants;
import ru.gds.spring.mongo.dto.BookContentDto;

import java.util.List;

@MessagingGateway
public interface Library {

    @Gateway(requestChannel = Constants.INPUT_CHANNEL, replyChannel = Constants.OUTPUT_CHANNEL)
    List<BookContentDto> process(List<String> orders);
}
