package ir.alirezaalijani.spring.mail.module.mail;

import ir.alirezaalijani.spring.mail.module.mail.model.BasicMailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * This Service is implemented already inside the module
 * You can implement your own implementation
 */
@Slf4j
@Service
public class MailServiceImpl extends MailService {

    /**
     * Default behavior of implemented service
     * this method is call inside MailMessageVisitor class automatically
     * @param mailMessage basic mail object that we send
     * @param success stats of mail sending
     */
    @Override
    protected void mailSend(BasicMailMessage mailMessage, boolean success) {
        if (success){
            log.info("Mail is Send Successfully to {}",mailMessage.getToMail());
        }else {
            log.error("Sending Mail to {} failed ",mailMessage.getToMail());
        }
    }

}
