package ru.gds.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.BridgeFrom;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import ru.gds.spring.service.BookContentService;

@Configuration
public class IntegrationConfig {

    private final BookContentService bookContentService;

    IntegrationConfig(BookContentService bookContentService) {
        this.bookContentService = bookContentService;
    }

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
    @BridgeFrom(value = Constants.OUTPUT_CHANNEL)
    public MessageChannel fileChannel() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow booksContentFlow() {
        return IntegrationFlows.from(Constants.INPUT_CHANNEL)
                .split()
                .handle(bookContentService, "getPagesForBook")
                .aggregate()
                .channel(Constants.OUTPUT_CHANNEL)
                .get();
    }
}
