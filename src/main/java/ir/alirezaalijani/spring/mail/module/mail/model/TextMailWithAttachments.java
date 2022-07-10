package ir.alirezaalijani.spring.mail.module.mail.model;

import ir.alirezaalijani.spring.mail.module.mail.MailMessageVisitor;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TextMailWithAttachments extends BasicMailMessage implements MailMessage, AttachmentsMailMessage{

    private Map<String,String> attachments;

    public TextMailWithAttachments(String toMail, String fromMail, String subject, String message, String actionUrl) {
        super(toMail, fromMail, subject, message, actionUrl);
    }

    @Override
    public void accept(MailMessageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public Map<String, String> attachments() {
        return attachments==null ? null:Collections.unmodifiableMap(attachments);
    }

    public void addAttachment(String name,String path){
        if (attachments==null) attachments=new HashMap<>();
        attachments.put(name,path);
    }

    public void addAttachment(String name, File file){
        if (attachments==null) attachments=new HashMap<>();
        attachments.put(name,file.getAbsolutePath());
    }
}
