package ru.gds.spring.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.annotation.DirtiesContext;
import ru.gds.spring.utils.Utils;

@SpringBootTest
@DirtiesContext
@ComponentScan({"ru.gds.spring"})
class BookContentServiceTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void libraryGatewayTest() {

        MessageChannel requestChannel = applicationContext.getBean("inputChannel", MessageChannel.class);

        PublishSubscribeChannel responseChannel
                = applicationContext.getBean("outputChannel", PublishSubscribeChannel.class);

        for (int i = 0; i < 100; i++)
            requestChannel.send(new GenericMessage<>(Utils.generateOrders()));

        assert responseChannel.subscribe(message -> System.out.println(message.getHeaders()));
    }

}
