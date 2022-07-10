package ir.alirezaalijani.spring.mail.module.mail.model;


import ir.alirezaalijani.spring.mail.module.mail.MailMessageVisitor;

import java.util.HashMap;
import java.util.Map;

public abstract class HtmlMail extends BasicMailMessage implements HtmlMailMessage, MailMessage {

    private final String templateHtml;
    private Map<String, Object> messageModel;

    public HtmlMail(String toMail, String fromMail, String subject, String message, String actionUrl, String templateHtml) {
        super(toMail, fromMail, subject, message, actionUrl);
        this.templateHtml = templateHtml;

        addAttr("to_mail", toMail);
        addAttr("from_mail", fromMail);
        addAttr("subject", getSubject());
        addAttr("from", fromMail);
        addAttr("action_url", actionUrl);
        addAttr("mail_text", message);
    }

    @Override
    public void accept(MailMessageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Map<String, Object> getAttrs() {
        return this.messageModel;
    }

    @Override
    public String getTemplateHtml() {
        return this.templateHtml;
    }

    public void addAttr(String key, String value) {
        if (messageModel == null) {
            messageModel = new HashMap<>();
        }
        messageModel.put(key, value);
    }
}
