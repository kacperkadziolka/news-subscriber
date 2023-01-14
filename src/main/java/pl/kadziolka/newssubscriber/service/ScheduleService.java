package pl.kadziolka.newssubscriber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

@Component
public class ScheduleService {

    @Autowired
    private TaskScheduler taskScheduler;

    @Autowired
    private EmailSenderService emailSenderService;



}
