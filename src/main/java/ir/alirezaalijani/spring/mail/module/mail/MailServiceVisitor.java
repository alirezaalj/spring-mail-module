package ir.alirezaalijani.spring.mail.module.mail;

import ir.alirezaalijani.spring.mail.module.mail.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.RetryOperations;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MailServiceVisitor implements MailMessageVisitor {

    private final JavaMailSender javaMailSender;
    private final MailService mailService;
    private final RetryOperations retryOperations;
    private final SpringTemplateEngine thymeleafTemplateEngine;

    public MailServiceVisitor(@Qualifier("my-spring-mail-sender") JavaMailSender javaMailSender,
                              MailService mailService,
                              @Qualifier("my-spring-mail-retry") RetryOperations retryOperations,
                              SpringTemplateEngine thymeleafTemplateEngine) {
        this.javaMailSender = javaMailSender;
        this.mailService = mailService;
        this.retryOperations = retryOperations;
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    @Override
    public void visit(HtmlMail o) {
        boolean result = false;
        try {
            MimeMessageHelper mimeMessageHelper = createMessageHelper(true);
            result = htmlBaseVisit(o, mimeMessageHelper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailService.mailSend(o, result);
    }

    @Override
    public void visit(TextMail o) {
        boolean result = false;
        try {
            MimeMessageHelper mimeMessageHelper = createMessageHelper(false);
            mimeMessageHelper.setFrom(o.getFromMail());
            mimeMessageHelper.setTo(o.getToMail());
            mimeMessageHelper.setSubject(o.getSubject());
            mimeMessageHelper.setText(o.getMessage());
            result = sendMail(o, mimeMessageHelper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailService.mailSend(o, result);
    }

    @Override
    public void visit(HtmlMailWithAttachments o) {
        boolean result = false;
        try {
            MimeMessageHelper mimeMessageHelper = addAttachments(o);
            result = htmlBaseVisit(o, mimeMessageHelper);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mailService.mailSend(o, result);
    }

    @Override
    public void visit(TextMailWithAttachments o) {
        boolean result=false;
        try {
            MimeMessageHelper mimeMessageHelper = addAttachments(o);
            mimeMessageHelper.setFrom(o.getFromMail());
            mimeMessageHelper.setTo(o.getToMail());
            mimeMessageHelper.setSubject(o.getSubject());
            mimeMessageHelper.setText(o.getMessage());
            result = sendMail(o, mimeMessageHelper);
        }catch (Exception e){
            e.printStackTrace();
        }
        mailService.mailSend(o, result);
    }

    private boolean htmlBaseVisit(HtmlMail o, MimeMessageHelper mimeMessageHelper) throws MessagingException {
        String html = generateHtmlMessage(o);
        mimeMessageHelper.setFrom(o.getFromMail());
        mimeMessageHelper.setTo(o.getToMail());
        mimeMessageHelper.setSubject(o.getSubject());
        if (html != null)
            mimeMessageHelper.setText(html, true);
        else mimeMessageHelper.setText(o.getMessage());
        return sendMail(o, mimeMessageHelper);
    }

    private boolean sendMail(BasicMailMessage mailMessage, MimeMessageHelper messageHelper) {
        try {
            return retryOperations.execute(context -> {
                if (context.getRetryCount() == 0) {
                    log.info("Send new Mail From {} To {}", mailMessage.getFromMail(), mailMessage.getToMail());
                } else {
                    log.error("Error at Send Email message: {}", context.getLastThrowable().getMessage());
                    log.info("Retry send email From {} ,To {} for the {} time", mailMessage.getFromMail(), mailMessage.getToMail(), context.getRetryCount());
                }
                javaMailSender.send(messageHelper.getMimeMessage());
                return true;
            });
        } catch (Exception e) {
            log.error("Exit retry email sending message: {}", e.getMessage());
        }
        return false;
    }

    private String generateHtmlMessage(HtmlMailMessage htmlMailMessage) {
        Context thymeleafContext = new Context();
        Map<String, Object> templateVariables = new HashMap<>(htmlMailMessage.getAttrs());
        templateVariables.put("date", new Date());
        thymeleafContext.setVariables(templateVariables);
        try {
            return thymeleafTemplateEngine.process(htmlMailMessage.getTemplateHtml(), thymeleafContext);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private MimeMessageHelper addAttachments(AttachmentsMailMessage attachmentsMailMessage)
            throws MessagingException {
        MimeMessageHelper messageHelper = createMessageHelper();
        for (Map.Entry<String, String> entry : attachmentsMailMessage.attachments().entrySet()) {
            messageHelper.addAttachment(entry.getKey(), new File(entry.getValue()));
        }
        return messageHelper;
    }

    private MimeMessageHelper createMessageHelper(boolean multiPart) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        return new MimeMessageHelper(mimeMessage, multiPart, "UTF-8");
    }

    private MimeMessageHelper createMessageHelper() throws MessagingException {
        return createMessageHelper(true);
    }
}
