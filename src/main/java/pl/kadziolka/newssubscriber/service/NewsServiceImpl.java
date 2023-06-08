package pl.kadziolka.newssubscriber.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kadziolka.newssubscriber.model.Article;
import pl.kadziolka.newssubscriber.model.Example;

import java.util.ArrayList;

@Service
public class NewsServiceImpl implements NewsService{

    private RestTemplate restTemplate;

    @Value("${external.api.key}")
    private String apiKey;

    private static final String baseUrlForTopHeadlines = "https://newsapi.org/v2/everything?q=";

    @Autowired
    public NewsServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ArrayList<Article> getArrayListOfArticles(String keyword) throws JsonProcessingException {
        String keywordResponse = restTemplate.getForObject(baseUrlForTopHeadlines + keyword + "&language=en" + apiKey, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Example example = objectMapper.readValue(keywordResponse, Example.class);

        ArrayList<Article> articleArrayList = new ArrayList<>(example.getArticles());
        return articleArrayList;
    }

    @Override
    public Article getRandomArticleFromListOfArticles(ArrayList<Article> articleArrayList) {
        int index = (int) (Math.random() * articleArrayList.size());
        Article article = articleArrayList.get(index);
        return new Article(article.getTitle(), article.getDescription(), article.getUrl(), index);
    }
}
