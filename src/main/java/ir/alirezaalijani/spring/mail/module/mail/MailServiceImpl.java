package ir.alirezaalijani.spring.mail.module.mail;

import ir.alirezaalijani.spring.mail.module.mail.model.BasicMailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MailServiceImpl extends MailService {


    @Override
    protected void mailSend(BasicMailMessage mailMessage, boolean success) {
        if (success){
            log.info("Mail is Send Successfully to {}",mailMessage.getToMail());
        }else {
            log.error("Sending Mail to {} failed ",mailMessage.getToMail());
        }
    }

}
