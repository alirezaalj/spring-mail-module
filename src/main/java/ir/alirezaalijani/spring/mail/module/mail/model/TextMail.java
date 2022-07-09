package ir.alirezaalijani.spring.mail.module.mail.model;


import ir.alirezaalijani.spring.mail.module.mail.MailMessageVisitor;

public class TextMail extends BasicMailMessage implements MailMessage {

    public TextMail(String toMail, String fromMail, String subject, String message, String actionUrl) {
        super(toMail, fromMail, subject, message,actionUrl);
    }

    @Override
    public void accept(MailMessageVisitor visitor) {
         visitor.visit(this);
    }
}
