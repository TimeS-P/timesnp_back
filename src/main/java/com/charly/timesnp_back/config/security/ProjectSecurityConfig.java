package com.charly.timesnp_back.config.security;

import com.charly.timesnp_back.exceptionhandling.CustomAccessDeniedHandler;
import com.charly.timesnp_back.exceptionhandling.TimeSnpAuthenticationEntryPoint;
import com.charly.timesnp_back.filter.*;
import com.charly.timesnp_back.filter.AuthoritiesLoggingAfterFilters;
import com.charly.timesnp_back.filter.CsrfCookieFilter;
import com.charly.timesnp_back.filter.JWTTokenGeneratorFilter;
import com.charly.timesnp_back.filter.JWTTokenValidatorFilter;
import com.charly.timesnp_back.repositories.UsuarioRepository;
import com.charly.timesnp_back.services.PerfilServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
@EnableWebSecurity
@RequiredArgsConstructor
public class ProjectSecurityConfig {

    // Inyectamos el bean de la clase TimeSnpAuthenticationEntryPoint por constructor
    private final TimeSnpAuthenticationEntryPoint timeSnpAuthenticationEntryPoint;
    private final PerfilServiceImpl perfilService;
    private final UsuarioRepository usuarioRepository;
    private final MdcFilter mdcFilter;

    private final Environment env;

    /**
     * This method is in charge of creating the security filter chain
     * @param http HttpSecurity object
     * @return SecurityFilterChain object to be used in the security configuration
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RateLimitingFilter rateLimitingFilter) throws Exception {

        // Para manejar el token CSRF que se manda en la request (El token que se manda en la Cookie se maneja automáticamente)
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        // No guardar la sesión en el servidor (El token jwt no se guarda en el servidor, solo lo enviaremos al cliente)
        http.sessionManagement(sesionConfig -> sesionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(corsConfig -> corsConfig.configurationSource(
                        new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration config = new CorsConfiguration();
                                // Origins permitidos (React vite dev server)
                                config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
                                // Permitir todos los métodos (GET, POST, PUT, DELETE, etc)
                                config.setAllowedMethods(Collections.singletonList("*"));
                                // Accepting credentials (cookies, authorization,etc)
                                config.setAllowCredentials(true);
                                // Permitir todos los headers
                                config.setAllowedHeaders(Collections.singletonList("*"));
                                // Exponer el header Authorization para enviar el token JWT
                                config.setExposedHeaders(Arrays.asList("Authorization"));
                                config.setMaxAge(3600L); // 1 hora
                                return config;
                            }
                        }
                ))
                .csrf(
                        csrfConfig -> csrfConfig
                                .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                                .ignoringRequestMatchers( // Ignorar estas rutas para la protección CSRF
                                        "/api/auth/register",
                                        "/api/auth/loginSecure"
                                )
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // Para que el token CSRF sea accesible desde el cliente
                )
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class) // Este filtro se ejecuta después de la autenticación básica
                .addFilterAfter(new AuthoritiesLoggingAfterFilters(), BasicAuthenticationFilter.class) // Este filtro se ejecuta después de la autenticación básica
                .addFilterAfter(new JWTTokenGeneratorFilter(perfilService), BasicAuthenticationFilter.class) // Se genera el token JWT después de la autenticación básica al hacer login
                .addFilterBefore(new JWTTokenValidatorFilter(env, usuarioRepository), BasicAuthenticationFilter.class) // Se valida el token JWT antes de la autenticación básica cada vez que se hace una petición
                .addFilterBefore(rateLimitingFilter, JWTTokenValidatorFilter.class) // Se valida el rate limiting antes de la validación del token JWT y la autenticación básica
                .addFilterAfter(mdcFilter, BasicAuthenticationFilter.class) // Se añade el filtro MDC para el manejo de logs
                .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure());// ONLY HTTP
                //.csrf(AbstractHttpConfigurer::disable); // Desactivamos la protección CSRF (Cross-Site Request Forgery) temporalmente

        // Configuramos las rutas que requieren autenticación
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers( // Rutas que requieren autenticación
                        "/api/testing/private",
                        "/api/cambiar_contrasena",
                        "/api/forgot_password",
                        "/api/validate_token",
                        "/api/resources/upload",
                        "/api/resources/delete",
                        "/api/updateUserInfo",
                        "/api/combo/get-roles",
                        "/api/chat/create-chat",
                        "/api/usuario/getId",
                        "/api/chat/get-mensajes",
                        "/api/chat/get-chats-usuario"
                ).authenticated()
                .requestMatchers( // RUTAS QUE REQUIEREN ROL USUARIO UNICAMENTE
                        "/api/resources/gcp/download/**",
                        "/api/verification/email/**",
                        "/api/verification/ine"
                ).hasRole("USUARIO")
                .requestMatchers( // RUTAS QUE REQUIEREN ROL VERIFICADOR UNICAMENTE
                        "/api/resources/gcp/signed-url/**",
                        "/api/verification/ine/verify"
                ).hasRole("VERIFICADOR")
                .requestMatchers( // RUTAS QUE REQUIEREN ROL USUARIO O PROVEEDOR
                        "/api/resources/gcp/upload",
                        "/api/resources/gcp/delete/**"
                ).hasAnyRole("USUARIO", "PROVEEDOR")
                .requestMatchers( // RUTAS QUE REQUIEREN ROL VERIFICADOR UNICAMENTE
                        "/api/send-accept-verification",
                        "/api/send-denied-verification"
                ).hasRole("VERIFICADOR")
                .requestMatchers( // RUTAS PARA ADMINISTRADORES
                        "/api/testing/private/admin"
                ).hasRole("ADMIN")
                .requestMatchers( // RUTAS PARA PROVEEDORES
                        "/api/combo/create-combo",
                        "/api/combo/get-combos-by-proveedor",
                        "/api/combo/delete-combo/{id}",
                        "/api/chat/get-chats-proveedor"
                ).hasRole("PROVEEDOR")
                .requestMatchers(
                        "/api/testing/public",
                        "/api/auth/**",
                        "/api/contact",
                        "/api/send-email",
                        "/error",
                        "/invalidSession",
                        "/api/servicios/serviciosCategoria",
                        "/api/servicios/servicio",
                        "api/combo/get-combos",
                        "/api/chat/get-servicio-general",
                        "/chat/**",
                        "/topic/chat/**",
                        "/ws-chat/**"
                        "/api/categorias/obtenerCategoriasServicios"
                ).permitAll()
        );

        //http.formLogin(withDefaults());
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

    @Bean
    public RateLimitingFilter rateLimitingFilter() {
        return new RateLimitingFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {

        // Usamos nuestro custom authentication provider
        TimeSnpUsernamePwdAuthenticationProvider authenticationProvider = new TimeSnpUsernamePwdAuthenticationProvider(
                        (TimeSnpUserDetailsService) userDetailsService,
                        passwordEncoder
        );

        // Configuramos el authentication manager
        // ProviderManager es la implementación por defecto de AuthenticationManager
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        // Configuramos el authentication manager para que no borre las credenciales después de la autenticación
        providerManager.setEraseCredentialsAfterAuthentication(false);

        // Retornamos el authentication manager
        return providerManager;

    }

}
