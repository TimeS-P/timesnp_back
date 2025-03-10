package com.charly.timesnp_back.config.security;

import com.charly.timesnp_back.exceptionhandling.CustomAccessDeniedHandler;
import com.charly.timesnp_back.exceptionhandling.TimeSnpAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
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

        http.sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession").maximumSessions(10))
                .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())// ONLY HTTP
                .csrf(AbstractHttpConfigurer::disable); // Desactivamos la protección CSRF (Cross-Site Request Forgery) temporalmente

        // Configuramos las rutas que requieren autenticación
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(
                        "/api/testing"
                ).authenticated()
                .requestMatchers(
                        "/api/auth/**",
                        "/api/contact",
                        "/api/forgot_password",
                        "/api/validate_token",
                        "/api/cambiar_contrasena",
                        "/api/send-email", //En teoría tenemos que proteger esta ruta, sin embargo el usuario no está logueado para este punto, por lo que habrá que resolverlo
                        "/error",
                        "/invalidSession"
                ).permitAll()
        );

        http.formLogin(withDefaults());
        // Configuramos la autenticacion basica
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(timeSnpAuthenticationEntryPoint));
        // GLOGAL CONFIGURATION FOR EXCEPTION HANDLING
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Default password encoder (bcrypt)
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
