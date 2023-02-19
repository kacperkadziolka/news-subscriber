package pl.kadziolka.newssubscriber.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pl.kadziolka.newssubscriber.model.Article;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private JavaMailSender javaMailSender;

    private NewsService newsService;

    @Value("${spring.mail.username}")
    private String sender;

    private Logger logger = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender, NewsService newsService) {
        this.javaMailSender = javaMailSender;
        this.newsService = newsService;
    }

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
            javaMailSender.send(simpleMailMessage);
            logger.info("Mail sent successfully...");
        }
        catch (Exception e) {
            logger.info("Error while sending mail " + e);
        }
    }

    @Override
    public void sendRegistrationMail(String to, String subject, String text, boolean isHTMLContent) throws MessagingException {
        //TODO: exception
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text, isHTMLContent);
        javaMailSender.send(mimeMessage);
    }

}
