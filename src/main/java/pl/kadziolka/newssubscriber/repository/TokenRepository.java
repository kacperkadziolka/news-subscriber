package pl.kadziolka.newssubscriber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kadziolka.newssubscriber.model.Token;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByTokenValue(String tokenValue);

}
