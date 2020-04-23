package ru.gds.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.BridgeFrom;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import ru.gds.spring.interfaces.Library;
import ru.gds.spring.mongo.dto.BookContentDto;
import ru.gds.spring.utils.Utils;

import java.util.Collection;
import java.util.List;

@SpringBootApplication
@IntegrationComponentScan
@EnableIntegration
public class Main {

    @Bean
    public QueueChannel inputChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel outputChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(5).get();
    }

    @Bean
    @BridgeFrom(value = "outputChannel")
    public MessageChannel fileChannel1() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow booksContentFlow() {
        return IntegrationFlows.from("inputChannel")
                .split()
                .handle("bookContentService", "getPagesForBooks")
                .aggregate()
                .channel("outputChannel")
                .get();
    }

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Main.class);

        try {
            Library library = context.getBean(Library.class);

            Collection bookContentDtos = library.process(Utils.generateOrders());
            List<BookContentDto> list = ((List<List<BookContentDto>>) bookContentDtos).get(0);
            list.forEach(bookContentDto -> Utils.printPages(bookContentDto));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
