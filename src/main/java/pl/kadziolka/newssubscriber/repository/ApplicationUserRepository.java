package pl.kadziolka.newssubscriber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kadziolka.newssubscriber.model.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByUsername(String username);

}
