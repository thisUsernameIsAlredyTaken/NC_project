package com.example.project.config;

import com.example.project.service.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()
                .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/movie/**").permitAll()
                .antMatchers(HttpMethod.POST, "/movie/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/movie/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/movie/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/movie/**").hasRole("ADMIN")
                .antMatchers("/user/p/**", "/user", "/user/find").hasRole("ADMIN")
                .antMatchers("/user/me/**").hasRole("USER")
            .and()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
