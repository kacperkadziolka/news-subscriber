package pl.kadziolka.newssubscriber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kadziolka.newssubscriber.model.ApplicationUser;
import pl.kadziolka.newssubscriber.repository.ApplicationUserRepository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ApplicationUser> applicationUser = applicationUserRepository.findByUsername(username);

        return applicationUser.map(element -> applicationUserRepository.findByUsername(element.getUsername()).get())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
    }
}
