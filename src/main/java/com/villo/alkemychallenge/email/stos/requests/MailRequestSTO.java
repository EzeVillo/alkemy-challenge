package com.villo.alkemychallenge.email.stos.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MailRequestSTO {
    private List<Message> messages;

    @Getter
    @Setter
    @Builder
    public static class UserMail {
        public String email;
        public String name;
    }

    @Getter
    @Setter
    @Builder
    public static class Message {
        public UserMail from;
        public List<UserMail> to;
        public String subject;
        public String textPart;
        public String hTMLPart;
        public String customID;
    }
}
