package pl.kadziolka.newssubscriber.service;

import pl.kadziolka.newssubscriber.model.Article;

import java.util.ArrayList;

public interface EmailSenderService {

    void sendEmail(String to, ArrayList<Article> articleArrayList);

}
