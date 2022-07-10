package ir.alirezaalijani.spring.mail.module.mail.templates;

public enum TemplateType {
    Red("mail/template_red.html"),
    Blue("mail/template_blue.html");

    public final String label;

    private TemplateType(String label) {
        this.label = label;
    }
}
