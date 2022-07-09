package ir.alirezaalijani.spring.mail.module.mail.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TextMailWithAttachments extends TextMail implements AttachmentsMailMessage{

    private Map<String,String> attachments;

    public TextMailWithAttachments(String toMail, String fromMail, String subject, String message, String actionUrl) {
        super(toMail, fromMail, subject, message, actionUrl);
    }

    @Override
    public Map<String, String> attachments() {
        return Collections.unmodifiableMap(attachments);
    }

    public void addAttachment(String name,String path){
        if (attachments==null) attachments=new HashMap<>();
        attachments.put(name,path);
    }
}
