package com.joint_walks.java_alesyapesetskaya.config;

import com.joint_walks.java_alesyapesetskaya.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl service;




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(service).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/dogwalker/**").authenticated()
                .and()
                .formLogin()
                .permitAll();


//        http
//                .authorizeHttpRequests()
//                .antMatchers("/login").permitAll()
//                    .antMatchers("/dogwalker/**").authenticated()
//                    .and()
//                .formLogin()
//                    .loginPage("/login")
//                .loginProcessingUrl("/process_login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .successHandler((request, response, authentication) -> {
//                    response.sendRedirect("/dogwalker/main");
//                })
//                .and()
//                .logout()
//                .logoutUrl("/perform_logout")
//                .addLogoutHandler((request, response, authentication) -> {
//                    request.getSession().invalidate();
//                });

    }
}
