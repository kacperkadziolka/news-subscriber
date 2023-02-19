package pl.kadziolka.newssubscriber.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Token {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String tokenValue;

    @OneToOne
    private ApplicationUser applicationUser;

}
