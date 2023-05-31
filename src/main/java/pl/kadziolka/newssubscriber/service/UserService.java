package pl.kadziolka.newssubscriber.service;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kadziolka.newssubscriber.component.EnvironmentUtil;
import pl.kadziolka.newssubscriber.model.ApplicationUser;
import pl.kadziolka.newssubscriber.model.Token;
import pl.kadziolka.newssubscriber.repository.ApplicationUserRepository;
import pl.kadziolka.newssubscriber.repository.TokenRepository;

import java.net.UnknownHostException;
import java.util.UUID;

@Service
public class UserService {

    private ApplicationUserRepository applicationUserRepository;

    private TokenRepository tokenRepository;

    private PasswordEncoder passwordEncoder;

    private EmailSenderService emailSenderService;

    private EnvironmentUtil environmentUtil;

    @Autowired
    public UserService(ApplicationUserRepository applicationUserRepository, PasswordEncoder passwordEncoder,
                       TokenRepository tokenRepository, EmailSenderService emailSenderService, EnvironmentUtil environmentUtil) {
        this.applicationUserRepository = applicationUserRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailSenderService = emailSenderService;
        this.environmentUtil = environmentUtil;

    }

    public void addUser(ApplicationUser applicationUser) throws UnknownHostException, MessagingException {
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        applicationUser.setRole("ROLE_USER");
        applicationUserRepository.save(applicationUser);
        sendToken(applicationUser);
    }

    private void sendToken(ApplicationUser applicationUser) throws UnknownHostException, MessagingException {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setTokenValue(tokenValue);
        token.setApplicationUser(applicationUser);
        tokenRepository.save(token);

        String url = environmentUtil.getHostname() +
                //":" + environmentUtil.getPort() +
                "/token?tokenValue=" + tokenValue;
        String messageContent =
                "This is an email sent from News Subscriber to verify your email address. <br/>" +
                "Please, activate your account by clicking on the link or copy it into your browser. <br/>" +
                "<br/>" +
                url +
                "<br/> <br/>" +
                "Thanks, <br/>" +
                "Team News Subscriber";
        emailSenderService.sendRegistrationMail(applicationUser.getUsername(), "News Subscriber Registration Email", messageContent, true);
    }
}
