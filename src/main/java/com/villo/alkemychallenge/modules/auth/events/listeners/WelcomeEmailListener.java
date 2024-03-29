package com.villo.alkemychallenge.modules.auth.events.listeners;

import com.villo.alkemychallenge.modules.auth.converters.UserToMailRequestConverter;
import com.villo.alkemychallenge.modules.auth.events.UserCreatedEvent;
import com.villo.alkemychallenge.modules.email.integrations.MailjetClient;
import com.villo.alkemychallenge.utils.errors.IntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WelcomeEmailListener {
    private static final String WELCOME_MAIL_SUBJECT = "Bienvenido!";
    private static final String WELCOME_MAIL_MESSAGE = "Muchas gracias por crearte una cuenta!";

    private final MailjetClient mailjetClient;
    private final UserToMailRequestConverter userToMailRequestConverter;

    @EventListener
    @Async
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        var mailRequestSTO = userToMailRequestConverter.convertWithSubjectAndMessage(event.getUser(), WELCOME_MAIL_SUBJECT, WELCOME_MAIL_MESSAGE);

        try {
            mailjetClient.send(mailRequestSTO);
        } catch (IntegrationException ignored) {
        }
    }
}