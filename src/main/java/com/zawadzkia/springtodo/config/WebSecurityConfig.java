package com.zawadzkia.springtodo.config;

import com.zawadzkia.springtodo.user.UserRepository;
import com.zawadzkia.springtodo.auth.AppUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class WebSecurityConfig {


    private static final String[] allowedAnonymousAccessURIs = {"/", "/index", "/favicon.png", "/error", "/webjars/**", "/css/**", "/images/**", "/js/**", "/auth/**"};

    @Bean
    @Order(4)
    AuthenticationManager authenticationManager(AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(new AppUserDetailsService(userRepository));
        return authenticationManagerBuilder.authenticationProvider(authenticationProvider).getOrBuild();
    }

    @Bean
    @Order(8)
    SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authz -> {
                    authz
                            .requestMatchers(allowedAnonymousAccessURIs).permitAll()
                            .anyRequest().authenticated();
                })
                .logout(httpSecurityLogoutConfigurer -> {
                    httpSecurityLogoutConfigurer
                            .logoutUrl("/auth/logout");
                })
                .formLogin(
                        httpSecurityFormLoginConfigurer -> {
                            httpSecurityFormLoginConfigurer
                                    .loginProcessingUrl("/auth/process")
                                    .loginPage("/auth/login");
                        }
                )
                .build();
    }


}
