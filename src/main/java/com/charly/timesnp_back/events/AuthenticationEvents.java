package com.charly.timesnp_back.events;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j // For logging object inyection
public class AuthenticationEvents {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent) {
        // En caso de que querramos hacer auditoria para seguridad y manejo de usuarios y llevar controlados los accesos
        // Enviar correos de notificación, etc.
        log.info("Login successful for the user: {}", successEvent.getAuthentication().getName());

    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failureEvent) {
        // En caso de que querramos hacer auditoria para seguridad y manejo de usuarios y llevar controlados los accesos
        // Enviar correos de notificación, etc.
        log.error("Login failed for the user: {} due to: {}", failureEvent.getAuthentication().getName(), failureEvent.getException().getMessage());
    }

}
