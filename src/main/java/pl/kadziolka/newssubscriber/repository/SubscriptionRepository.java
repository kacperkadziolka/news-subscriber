package pl.kadziolka.newssubscriber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kadziolka.newssubscriber.model.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("FROM Subscription WHERE USERNAME_OWNER = :searchValue")
    List<Subscription> getSubscriptionByUsernameOwner(String searchValue);

}
