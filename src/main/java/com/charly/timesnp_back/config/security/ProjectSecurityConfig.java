package com.charly.timesnp_back.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ProjectSecurityConfig {

    // Inyectamos el bean de la clase TimeSnpAuthenticationEntryPoint por constructor
    private final TimeSnpAuthenticationEntryPoint timeSnpAuthenticationEntryPoint;

    /**
     * This method is in charge of creating the security filter chain
     * @param http HttpSecurity object
     * @return SecurityFilterChain object to be used in the security configuration
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // Desactivamos la protección CSRF (Cross-Site Request Forgery) temporalmente
        http.csrf(AbstractHttpConfigurer::disable);

        // Configuramos las rutas que requieren autenticación
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(
                        "/api/testing"
                ).authenticated()
                .requestMatchers(
                        "/api/auth/**",
                        "/api/contact",
                        "/api/forgot_password",
                        "/api/send-email", //En teoría tenemos que proteger esta ruta, sin embargo el usuario no está logueado para este punto, por lo que habrá que resolverlo
                        "/error"
                ).permitAll()
        );

        // Configuramos la autenticacion basica
        http.httpBasic(basic -> basic.authenticationEntryPoint(timeSnpAuthenticationEntryPoint));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Default password encoder (bcrypt)
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
