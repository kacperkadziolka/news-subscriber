package pl.kadziolka.newssubscriber.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kadziolka.newssubscriber.model.Article;
import pl.kadziolka.newssubscriber.model.Subscription;
import pl.kadziolka.newssubscriber.service.EmailSenderService;
import pl.kadziolka.newssubscriber.service.NewsService;
import pl.kadziolka.newssubscriber.service.SubscriptionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Controller
public class EmailSenderController {

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private SubscriptionService subscriptionService;

    private Map<Long, ScheduledFuture<?>> jobsMap = new HashMap<>();

    private ScheduledFuture<?> scheduledFuture;

    @GetMapping
    public String getAllSubscriptions(Model model) {
        List<Subscription> allSubscriptions = subscriptionService.getSubscriptions();
        model.addAttribute("allSubscriptions", allSubscriptions);
        return "index";
    }


    @PostMapping("/sendEmail")
    public String sendEmail(@ModelAttribute Subscription subscription) throws JsonProcessingException {
        subscriptionService.saveSubscription(subscription);

        TimeUnit timeUnit = null;
        if (subscription.getTimeUnit().equals("SECONDS")) {
            timeUnit = TimeUnit.SECONDS;
        }
        else if (subscription.getTimeUnit().equals("MINUTES")) {
            timeUnit = TimeUnit.MINUTES;
        }
        else if (subscription.getTimeUnit().equals("HOURS")) {
            timeUnit = TimeUnit.HOURS;
        }
        else if (subscription.getTimeUnit().equals("DAYS")) {
            timeUnit = TimeUnit.DAYS;
        }

        ArrayList<Article> articleArrayList = newsService.getArrayListOfArticles(subscription.getNewsTopic());

        scheduledFuture = taskScheduler.schedule(() -> emailSenderService.sendEmail(
               subscription.getEmailAddress(), articleArrayList), new PeriodicTrigger(subscription.getPeriod(), timeUnit));
        jobsMap.put(subscription.getId(), scheduledFuture);

        return "redirect:/";
    }

    @GetMapping("/delete")
    public String removeEmailScheduler(@RequestParam Long id) {
        ScheduledFuture<?> scheduledFuture = jobsMap.get(id);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            jobsMap.put(id, null);
        }
        subscriptionService.deleteSubscription(id);
        return "redirect:/";
    }

}
