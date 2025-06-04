package com.example.WorkWithAirline.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((authorize) ->
            authorize
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/register").permitAll()
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/logout").permitAll()
                    .requestMatchers("/home").hasAnyRole("admin","user")
                    .requestMatchers("/passengers/create").hasAnyRole("admin")
                    .requestMatchers("/profiles").hasAnyRole("admin")
                    .requestMatchers("/ways").hasAnyRole("admin")
                    .requestMatchers("/airplanes").hasAnyRole("admin")
                    .requestMatchers("/departments").hasAnyRole("admin")
                    .requestMatchers("/routes").hasAnyRole("admin")
                    .requestMatchers("/luggages").hasAnyRole("admin")
                    .requestMatchers("/personals").hasAnyRole("admin")
                    .requestMatchers("/passengers").hasAnyRole("admin")
                    .requestMatchers("/tickets").hasAnyRole("admin")
                    .requestMatchers("/users").hasAnyRole("admin")
                    .requestMatchers("/adm").hasAnyRole("admin")

                    .anyRequest().authenticated()
    ).formLogin(
            form -> form
                    .defaultSuccessUrl("/home")
                    .permitAll()
    ).logout(config -> config.logoutSuccessUrl("/home"));

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }
}