package joelsonfatima.configuration;

import joelsonfatima.auth.JwtAuthEntryPoint;
import joelsonfatima.auth.JwtAuthFilter;
import joelsonfatima.dto.ROLES;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAuthFilter jwtAuthFilter;
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityContext().requireExplicitSave(false)
                .and()
                .csrf().disable()
                .cors().configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Collections.singletonList("*"));
                    configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                    configuration.setAllowedHeaders(Collections.singletonList("*"));
                    configuration.setMaxAge(3600L);
                    return configuration;
                })
                .and()
                .anonymous().disable()
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                            .requestMatchers(HttpMethod.POST, "/authentication/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/authentication/info").permitAll()
                            .requestMatchers(HttpMethod.GET, "/drivers/{cin}").hasAnyRole(ROLES.ROLE_ADMIN.getShortName(), ROLES.ROLE_DRIVER.getShortName())
                            .requestMatchers(HttpMethod.PUT, "/drivers/{cin}").hasAnyRole(ROLES.ROLE_ADMIN.getShortName(), ROLES.ROLE_DRIVER.getShortName())
                            .requestMatchers(HttpMethod.GET, "/drivers/{cin}/trips").hasAnyRole(ROLES.ROLE_ADMIN.getShortName(), ROLES.ROLE_DRIVER.getShortName())
                            .requestMatchers(HttpMethod.GET, "/drivers/{cin}/driverLicenses/{licenseId}").hasAnyRole(ROLES.ROLE_ADMIN.getShortName(), ROLES.ROLE_DRIVER.getShortName())
                            .requestMatchers(HttpMethod.GET, "/drivers/{cin}/driverLicenses").hasAnyRole(ROLES.ROLE_ADMIN.getShortName(), ROLES.ROLE_DRIVER.getShortName())
                            .requestMatchers(HttpMethod.GET, "/drivers/{cin}/vacations/{vacationId}").hasAnyRole(ROLES.ROLE_ADMIN.getShortName(), ROLES.ROLE_DRIVER.getShortName())
                            .requestMatchers(HttpMethod.GET, "/drivers/{cin}/vacations").hasAnyRole(ROLES.ROLE_ADMIN.getShortName(), ROLES.ROLE_DRIVER.getShortName())
                            .requestMatchers("/trips/**").hasAnyRole(ROLES.ROLE_ADMIN.getShortName(), ROLES.ROLE_MANAGER_TRIP.getShortName())
                            .requestMatchers(HttpMethod.GET, "/drivers").hasAnyRole(ROLES.ROLE_ADMIN.getShortName(), ROLES.ROLE_MANAGER_TRIP.getShortName())
                            .requestMatchers(HttpMethod.GET, "/vehicles").hasAnyRole(ROLES.ROLE_ADMIN.getShortName(), ROLES.ROLE_MANAGER_TRIP.getShortName())
                            .requestMatchers("/**").hasRole(ROLES.ROLE_ADMIN.getShortName())
                            .anyRequest().authenticated();
                })
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}