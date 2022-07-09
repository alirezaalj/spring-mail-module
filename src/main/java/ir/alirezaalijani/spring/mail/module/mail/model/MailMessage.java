package ir.alirezaalijani.spring.mail.module.mail.model;

import ir.alirezaalijani.spring.mail.module.mail.MailMessageVisitor;

public interface MailMessage {
    void accept(MailMessageVisitor visitor);
}
