package pl.kadziolka.newssubscriber.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kadziolka.newssubscriber.model.ApplicationUser;
import pl.kadziolka.newssubscriber.model.Article;
import pl.kadziolka.newssubscriber.model.Subscription;
import pl.kadziolka.newssubscriber.service.EmailSenderService;
import pl.kadziolka.newssubscriber.service.NewsService;
import pl.kadziolka.newssubscriber.service.SubscriptionService;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Controller
public class EmailSenderController {

    private EmailSenderService emailSenderService;

    private NewsService newsService;

    private TaskScheduler taskScheduler;

    private SubscriptionService subscriptionService;

    private Map<Long, ScheduledFuture<?>> jobsMap = new HashMap<>();

    private ScheduledFuture<?> scheduledFuture;

    @Autowired
    public EmailSenderController(EmailSenderService emailSenderService, NewsService newsService, TaskScheduler taskScheduler, SubscriptionService subscriptionService) {
        this.emailSenderService = emailSenderService;
        this.newsService = newsService;
        this.taskScheduler = taskScheduler;
        this.subscriptionService = subscriptionService;
    }

    @GetMapping("/getsubscriptions")
    public String getAllSubscriptions(Model model, @AuthenticationPrincipal ApplicationUser applicationUser) {
        List<Subscription> allSubscriptions = subscriptionService.getSubscriptionsForOwner(applicationUser.getUsername());
        model.addAttribute("allSubscriptions", allSubscriptions);
        System.out.println(applicationUser.getUsername());
        return "index";
    }


    @PostMapping("/sendEmail")
    public String sendEmail(@ModelAttribute Subscription subscription, @AuthenticationPrincipal ApplicationUser applicationUser) throws JsonProcessingException {
        subscription.setUsernameOwner(applicationUser.getUsername());
        subscription.setEmailAddress(applicationUser.getUsername());
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

        scheduledFuture = taskScheduler.schedule(() -> {
            try {
                emailSenderService.sendEmail(
                       subscription.getEmailAddress(), articleArrayList);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }, new PeriodicTrigger(subscription.getPeriod(), timeUnit));


        jobsMap.put(subscription.getId(), scheduledFuture);

        return "redirect:/getsubscriptions";
    }

    @GetMapping("/delete")
    public String removeEmailScheduler(@RequestParam Long id) {
        ScheduledFuture<?> scheduledFuture = jobsMap.get(id);
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            jobsMap.put(id, null);
        }
        subscriptionService.deleteSubscription(id);
        return "redirect:/getsubscriptions";
    }
}
