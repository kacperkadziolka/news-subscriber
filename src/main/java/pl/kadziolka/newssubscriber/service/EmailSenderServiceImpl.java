package pl.kadziolka.newssubscriber.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.kadziolka.newssubscriber.model.Article;

import java.util.ArrayList;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private NewsService newsService;

    @Value("${spring.mail.username}")
    private String sender;

    private Logger logger = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    @Override
    @Async
    public void sendEmail(String to, ArrayList<Article> articleArrayList) {
        Article article = newsService.getRandomArticleFromListOfArticles(articleArrayList);


        String messageBody = "Description: " + article.getDescription() + "<br> Source URL: " + article.getUrl();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(article.getTitle());
        simpleMailMessage.setText(messageBody);

        try {
            //System.out.println("********************* SLEEPING NOW FOR TESTING PURPOSE **********************");
            //Thread.sleep(60000);
            javaMailSender.send(simpleMailMessage);
            logger.info("Mail sent successfully...");
        }
        catch (Exception e) {
            logger.info("Error while sending mail " + e);
        }
    }

}
