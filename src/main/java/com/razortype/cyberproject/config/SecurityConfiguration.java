package com.razortype.cyberproject.config;

import com.razortype.cyberproject.core.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final CookieService cookieService;

    @Value("${application.cors.allowed-origins}")
    private List<String> allowedOrigins;

    @Value("${application.cors.allowed-methods}")
    private List<String> allowedMethods;

    @Value("${application.cors.allowed-headers}")
    private List<String> allowedHeaders;

    @Bean
    @Order(1)
    @Profile("dev")
    public SecurityFilterChain swaggerFilterChain(HttpSecurity http) throws Exception {
        return http
            .securityMatcher("/v3/**", "/bus/v3/**")
            .authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .requestMatchers("/v3/swagger-ui/**").permitAll()
                    .requestMatchers("/bus/v3/api-docs/**").permitAll())
            .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .securityMatcher("/api/v1/**")
            .csrf(AbstractHttpConfigurer::disable)
            .cors((cors) -> cors
                    .configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/api/v1/server/state").permitAll()
                    .requestMatchers("/api/v1/server/**").hasAnyAuthority(Role.USER.name())
                    .requestMatchers("/api/v1/attack-job/**").hasAnyAuthority(Role.MANAGER.name(), Role.ADMIN.name())
                    .requestMatchers("/api/v1/log/**").hasAnyAuthority(Role.AGENT.name(), Role.MANAGER.name(), Role.ADMIN.name())

                    .requestMatchers("/connect-agent").hasAnyAuthority(Role.AGENT.name())
                    .requestMatchers("/live-agent").hasAnyAuthority(Role.MANAGER.name(), Role.ADMIN.name())

                    .requestMatchers("/api/v1/test/**").permitAll()
                    .anyRequest().authenticated())
            .sessionManagement((session) -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .logout((logout) -> logout
                    .logoutUrl("/api/v1/auth/logout")
                    .addLogoutHandler(logoutHandler)
                    .logoutSuccessHandler((request, response, authentication) -> {
                        cookieService.setRefreshCookieNull();
                        SecurityContextHolder.clearContext();
                    })
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "refresh-token"));

            return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}