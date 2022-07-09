package ir.alirezaalijani.spring.mail.module.mail;

import ir.alirezaalijani.spring.mail.module.mail.model.BasicMailMessage;
import ir.alirezaalijani.spring.mail.module.mail.model.MailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

public abstract class MailService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    protected abstract void mailSend(BasicMailMessage mailMessage, boolean success);

    public void sendEmail(MailMessage mailMessage){
        eventPublisher.publishEvent(new MailSendEvent(mailMessage));
    }
}
