package ir.alirezaalijani.spring.mail.module.mail;

import ir.alirezaalijani.spring.mail.module.mail.model.MailMessage;
import org.springframework.context.ApplicationEvent;

public class MailSendEvent extends ApplicationEvent {
    private final MailMessage mailMessage;

    public MailSendEvent(MailMessage source) {
        super(source);
        this.mailMessage=source;
    }

    public MailMessage getMailMessage() {
        return mailMessage;
    }
}
