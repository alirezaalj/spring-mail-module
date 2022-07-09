package ir.alirezaalijani.spring.mail.module.mail;


import ir.alirezaalijani.spring.mail.module.mail.model.*;

import javax.mail.MessagingException;

public interface MailMessageVisitor {
    void visit(HtmlMail o);
    void visit(TextMail o);
    void visit(HtmlMailWithAttachments o);
    void visit(TextMailWithAttachments o);
}
