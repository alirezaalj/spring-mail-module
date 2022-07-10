package ir.alirezaalijani.spring.mail.module.mail.templates;

import ir.alirezaalijani.spring.mail.module.mail.model.HtmlMail;

public class DefaultMailTemplate extends HtmlMail {

    private final String titleText;
    private final String titleLink;
    private final String action_text;
    private final String company_name;
    private final String company_url;

    public DefaultMailTemplate(String toMail, String fromMail,
                               String subject, String message,
                               String actionUrl, TemplateType templateType,
                               String titleText, String titleLink,
                               String action_text,String company_name,
                               String company_url) {
        super(toMail, fromMail, subject, message, actionUrl, templateType.label);
        this.titleText=titleText;
        this.titleLink=titleLink;
        this.action_text=action_text;
        this.company_name=company_name;
        this.company_url=company_url;

        addAttr("title_text",titleText);
        addAttr("title_link",titleLink);
        addAttr("action_text",action_text);
        addAttr("company_name",company_name);
        addAttr("company_url",company_url);
    }

    public String getTitleText() {
        return titleText;
    }

    public String getTitleLink() {
        return titleLink;
    }

    public String getAction_text() {
        return action_text;
    }

    public String getCompany_name() {
        return company_name;
    }

    public String getCompany_url() {
        return company_url;
    }
}
