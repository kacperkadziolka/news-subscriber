package pl.kadziolka.newssubscriber.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.kadziolka.newssubscriber.model.Article;

import java.util.ArrayList;

public interface NewsService {

    ArrayList<Article> getArrayListOfArticles(String keyword) throws JsonProcessingException;

    Article getRandomArticleFromListOfArticles(ArrayList<Article> articleArrayList);

}
