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
//        http
////            .csrf()
////            .disable()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.OPTIONS, "/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//        http.authorizeRequests()
//            .antMatchers(HttpMethod.POST, "/movie/*")
//                .access("hasRole('ADMIN')")
//            .antMatchers(HttpMethod.DELETE, "movie/*")
//                .access("hasRole('ADMIN')")
//            .antMatchers(HttpMethod.PUT, "movie/*")
//                .access("hasRole('ADMIN')")
//            .antMatchers(HttpMethod.PATCH, "movie/*")
//                .access("hasRole('ADMIN')")
//            .antMatchers(HttpMethod.POST, "user/*")
//                .access("hasRole('ADMIN')")
//            .antMatchers(HttpMethod.DELETE, "user/*")
//                .access("hasRole('ADMIN')")
//            .antMatchers(HttpMethod.PUT, "user/*")
//                .access("hasRole('ADMIN')")
//            .antMatchers(HttpMethod.PATCH, "user/*")
//                .access("hasRole('ADMIN')")
//            .antMatchers(HttpMethod.GET, "movie/**")
//                .permitAll().anyRequest().authenticated()
//        .and().httpBasic();
        http
            .httpBasic()
                .and()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/movie/**").permitAll()
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

//    @Override
//    public void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user").password("{noop}123").roles("USER")
//                .and()
//                .withUser("__ADMIN").password("{noop}123").roles("USER", "ADMIN");
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        User.UserBuilder users = User.withDefaultPasswordEncoder();
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(users.username("user").password("123").roles("USER").build());
//        manager.createUser(users.username("admin").password("123").roles("USER", "ADMIN").build());
//        return manager;
//
//    }
}
