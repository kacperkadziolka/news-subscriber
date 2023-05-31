package pl.kadziolka.newssubscriber.service;

import javax.mail.MessagingException;
import pl.kadziolka.newssubscriber.model.Article;

import javax.mail.MessagingException;
import java.util.ArrayList;

public interface EmailSenderService {

    void sendEmail(String to, ArrayList<Article> articleArrayList) throws MessagingException;

    void sendRegistrationMail(String to, String subject, String text, boolean isHTMLContent) throws  MessagingException;
}
