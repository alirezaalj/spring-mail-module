package ir.alirezaalijani.spring.mail.module.mail.model;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HtmlMailWithAttachments extends HtmlMail implements AttachmentsMailMessage {

    private Map<String,String> attachments;

    public HtmlMailWithAttachments(String toMail, String fromMail, String subject, String message, String actionUrl, String templateHtml) {
        super(toMail, fromMail, subject, message, actionUrl, templateHtml);
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
