package ru.gds.spring.microservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gds.spring.microservice.services.CustomUserDetailsService;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    SecurityConfiguration(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("books", "books/{param}");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                    //.antMatchers("/books", "books", "books/{param}").authenticated()
                    .antMatchers( "books/{id}", "books/{bookId}").hasAnyRole("BOOKS_WRITE", "BOOKS_READ", "ADMINISTRATION")
                    .antMatchers("users","users/{param}","/users","users/{id}","users/{username}").hasAnyRole("USERS_WRITE", "ADMINISTRATION")
                    .antMatchers("/authors","/authors/{bookId}").hasAnyRole("AUTHORS_READ", "AUTHORS_WRITE", "ADMINISTRATION")
                    .antMatchers("/genres","/genres/{bookId}").hasAnyRole("GENRES_READ", "GENRES_WRITE", "ADMINISTRATION")
                    .antMatchers("/statuses","/statuses/{bookId}").hasAnyRole("STATUSES_READ", "STATUSES_WRITE", "ADMINISTRATION")
                    .antMatchers("/roles","/roles/{role}","/roles/{username}").hasAnyRole("ROLES_WRITE", "ADMINISTRATION")
                    .antMatchers("/**").permitAll()
                .and()
                    .formLogin()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                    .exceptionHandling();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return charSequence.toString().equals(s);
            }
        };
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
