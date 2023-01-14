package pl.kadziolka.newssubscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DemolicencjatApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemolicencjatApplication.class, args);
    }

}
