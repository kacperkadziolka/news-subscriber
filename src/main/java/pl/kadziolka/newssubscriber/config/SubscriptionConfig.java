package pl.kadziolka.newssubscriber.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kadziolka.newssubscriber.model.Subscription;
import pl.kadziolka.newssubscriber.repository.SubscriptionRepository;

import java.util.List;

@Configuration
public class SubscriptionConfig {

    @Bean
    CommandLineRunner commandLineRunner(SubscriptionRepository subscriptionRepository) {
        return args -> {
            Subscription subscription1 = new Subscription("test-email@gmail.com", "TestTopic", "seconds", 10);
            Subscription subscription2 = new Subscription("test-email2@gmail.com", "TestTopic2", "seconds", 10);

            subscriptionRepository.saveAll(List.of(subscription1, subscription2));
        };
    }

}
