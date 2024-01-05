package com.fatec.tcc.tccaudit.security.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fatec.tcc.tccaudit.models.entities.EmployeeRole;
import com.fatec.tcc.tccaudit.security.SecurityFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private SecurityFilter securityFilter;

    private static final String[] MATCHERS_GET = {
            "/questions/**",
            "/topics/**",
            "/summaries/**",
            "/answers/**",
            "/companies",
            "/employee/**",
            "/evidences/**"
    };

    private static final String[] SWAGGER = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .cors(corsCustomizer()).csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeHttpRequestsCustomizer -> {
                    authorizeHttpRequestsCustomizer
                            .requestMatchers(HttpMethod.POST, "/sign-up")
                            .hasRole(EmployeeRole.ADMIN.name())
                            .requestMatchers(HttpMethod.GET, "/departments")
                            .hasRole(EmployeeRole.ADMIN.name())
                            .requestMatchers(HttpMethod.GET, "/employees")
                            .hasRole(EmployeeRole.ADMIN.name())
                            .requestMatchers(HttpMethod.PUT, "/is-enabled/**")
                            .hasRole(EmployeeRole.ADMIN.name())
                            .requestMatchers(HttpMethod.POST, "/answers", "/upload")
                            .hasRole(EmployeeRole.EMPLOYEE.name())
                            .requestMatchers(HttpMethod.GET, MATCHERS_GET)
                            .hasRole(EmployeeRole.EMPLOYEE.name())
                            .requestMatchers(HttpMethod.DELETE, "/evidences/**")
                            .hasRole(EmployeeRole.EMPLOYEE.name())
                            .requestMatchers(HttpMethod.GET, "/via-cep").permitAll()
                            .requestMatchers(HttpMethod.POST, "/login", "/sign-up-company").permitAll()
                            .requestMatchers(SWAGGER).permitAll()
                            // .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                            // .permitAll()
                            .anyRequest()
                            .authenticated();
                }).addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    private Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Content-Disposition"));
        configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return cors -> cors.configurationSource(source);
    }

}