package ir.alirezaalijani.spring.mail.module.mail.model;

import java.util.Map;

public interface HtmlMailMessage {
    Map<String,Object> getAttrs();
    String getTemplateHtml();
}
