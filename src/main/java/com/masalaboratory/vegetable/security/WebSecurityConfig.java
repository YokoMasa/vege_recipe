package com.masalaboratory.vegetable.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${admin_tool.username:}")
    private String username;

    @Value("${admin_tool.password:}")
    private String password;

    @Autowired
    private Environment environment;

    private boolean isProduction() {
        Profiles prod = Profiles.of("prod");
        return environment.acceptsProfiles(prod); 
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (isProduction()) {
            http
                .authorizeRequests()
                    .antMatchers("/api/**").authenticated()
                    .anyRequest().permitAll()
                    .and()
                .httpBasic();
        } else {
            http
                .authorizeRequests()
                    .anyRequest().permitAll();
        }
        http
            .csrf().disable()
            .cors();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        if (isProduction()) {
            UserDetails user = User.builder().username(username).password(passwordEncoder().encode(password)).roles("USER").build();
            InMemoryUserDetailsManager service = new InMemoryUserDetailsManager();
            service.createUser(user);
            auth.userDetailsService(service);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}