package pl.kadziolka.newssubscriber.controller;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kadziolka.newssubscriber.model.ApplicationUser;
import pl.kadziolka.newssubscriber.model.Token;
import pl.kadziolka.newssubscriber.repository.ApplicationUserRepository;
import pl.kadziolka.newssubscriber.repository.TokenRepository;
import pl.kadziolka.newssubscriber.service.UserService;

import java.net.UnknownHostException;
import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;

    private TokenRepository tokenRepository;

    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    public UserController(UserService userService, TokenRepository tokenRepository, ApplicationUserRepository applicationUserRepository) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.applicationUserRepository = applicationUserRepository;
    }

    @GetMapping({"","/login"})
    public String loginPage() {
        return "start_page";
    }
    
    @PostMapping("/register")
    public String register(ApplicationUser applicationUser) throws UnknownHostException, MessagingException {
        // TODO: Handle the duplicate email in db
        userService.addUser(applicationUser);
        return "redirect:/";
    }

    @GetMapping("/token")
    public String token(@RequestParam String tokenValue) {
        Optional<Token> byTokenValue = tokenRepository.findByTokenValue(tokenValue);
        byTokenValue.map(token -> {
            ApplicationUser applicationUser = token.getApplicationUser();
            applicationUser.setEnabled(true);
            return applicationUserRepository.save(applicationUser);
        }).orElseThrow(RuntimeException::new);
        // ttt ryrr
        return "start_page";
    }
}
