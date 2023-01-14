package pl.kadziolka.newssubscriber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.kadziolka.newssubscriber.model.Subscription;
import pl.kadziolka.newssubscriber.repository.SubscriptionRepository;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public List<Subscription> getSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public void saveSubscription(Subscription subscription) {
        subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(Long id) {
        subscriptionRepository.deleteById(id);
    }

}
