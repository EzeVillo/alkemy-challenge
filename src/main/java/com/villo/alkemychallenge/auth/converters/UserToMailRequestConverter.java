package com.villo.alkemychallenge.auth.converters;

import com.villo.alkemychallenge.auth.entities.User;
import com.villo.alkemychallenge.email.stos.requests.MailRequestSTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
public class UserToMailRequestConverter implements Converter<User, MailRequestSTO> {
    private static final String WELCOME_MAIL_FROM_MAIL = "ezevillodevapi@gmail.com";
    private static final String WELCOME_MAIL_NAME = "Alkemy";

    @Override
    @NonNull
    public MailRequestSTO convert(User source) {
        return MailRequestSTO.builder()
                .messages(Collections.singletonList(MailRequestSTO.Message.builder()
                        .to(Collections.singletonList(MailRequestSTO.UserMail.builder()
                                .name(source.getUsername())
                                .email(source.getMail())
                                .build()))
                        .from(MailRequestSTO.UserMail.builder()
                                .name(WELCOME_MAIL_NAME)
                                .email(WELCOME_MAIL_FROM_MAIL)
                                .build())
                        .customID(UUID.randomUUID().toString())
                        .build()))
                .build();
    }

    public MailRequestSTO convertWithSubjectAndMessage(User user, String subject, String message) {
        var mailRequestSTO = this.convert(user);
        mailRequestSTO.getMessages().get(0).setSubject(subject);
        mailRequestSTO.getMessages().get(0).setHTMLPart(message);

        return mailRequestSTO;
    }
}
