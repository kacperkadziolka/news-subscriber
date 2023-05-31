package pl.kadziolka.newssubscriber.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.kadziolka.newssubscriber.service.UserDetailsServiceImpl;

@Configuration
public class SpringSecurityConfig {

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SpringSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        http.csrf().disable()
                .headers().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/getSubscriptions").authenticated())
                .formLogin().defaultSuccessUrl("/getSubscriptions");

         */


        http.csrf().disable()
                .headers().disable()
                .authorizeHttpRequests((authorize) -> authorize
                        .antMatchers("/getsubscriptions").authenticated()
                        //.antMatchers("/subscribe").authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/getsubscriptions")
                        .permitAll()
                ).logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .permitAll()
                );


        return http.build();
     }

}
