package ir.alirezaalijani.spring.mail.module.mail;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import ir.alirezaalijani.spring.mail.module.mail.model.HtmlMail;
import ir.alirezaalijani.spring.mail.module.mail.model.HtmlMailWithAttachments;
import ir.alirezaalijani.spring.mail.module.mail.model.TextMail;
import ir.alirezaalijani.spring.mail.module.mail.model.TextMailWithAttachments;
import ir.alirezaalijani.spring.mail.module.mail.templates.DefaultMailTemplate;
import ir.alirezaalijani.spring.mail.module.mail.templates.DefaultMailTemplateWithAttachments;
import ir.alirezaalijani.spring.mail.module.mail.templates.TemplateType;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("mailtest")
class MailServiceTest {

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("springboot", "duke"))
            .withPerMethodLifecycle(false);

    @Autowired
    private MailService mailService;

    @BeforeEach
    void beforeEach(){
        if(greenMail.isRunning()){
            greenMail.stop();
        }
        greenMail.start();
    }
    @AfterAll
    static void afterAll(){
        greenMail.stop();
    }

    @Test
    void sendTextEmailTest() throws MessagingException {
        TextMail textMail=createTextMail();
        mailService.sendEmail(textMail);
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getSubject()).isEqualTo(textMail.getSubject());
        assertThat(GreenMailUtil.getBody(receivedMessage)).isEqualTo(textMail.getMessage());
        assertThat(receivedMessage.getAllRecipients().length).isEqualTo(1);
    }

    @Test
    void sendHtmlEmailTest() throws MessagingException {
        DefaultMailTemplate htmlMail=createHtmlMail();
        mailService.sendEmail(htmlMail);
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getSubject()).isEqualTo(htmlMail.getSubject());
        assertThat(GreenMailUtil.getBody(receivedMessage)).contains(htmlMail.getTitleText());
        assertThat(receivedMessage.getAllRecipients().length).isEqualTo(1);
    }

    @Test
    void sendTextMailWithAttachmentsTest() throws MessagingException, IOException {
        TextMailWithAttachments mailMessage= createTextMailWithAttachment();
        File img1=new File("src/test/resources/data/img1.jpg");
        File img2=new File("src/test/resources/data/img2.jpg");
        assertThat(img1).exists();
        assertThat(img2).exists();
        mailMessage.addAttachment("my image.jpg",img1);
        mailMessage.addAttachment("my image2.jpg",img2);
        mailService.sendEmail(mailMessage);

        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getSubject()).isEqualTo(mailMessage.getSubject());
        assertThat(receivedMessage.getAllRecipients().length).isEqualTo(1);
        Multipart multiPart = (Multipart) receivedMessage.getContent();
        MimeBodyPart part1=(MimeBodyPart)multiPart.getBodyPart(0);
        assertThat(GreenMailUtil.getBody(part1)).contains("text/plain; charset=UTF-8");
        assertThat(GreenMailUtil.getBody(part1)).contains(mailMessage.getMessage());

        MimeBodyPart part2 = (MimeBodyPart) multiPart.getBodyPart(1);
        assertThat(Part.ATTACHMENT.equalsIgnoreCase(part2.getDisposition())).isTrue();
        assertThat(part2.getFileName()).contains("my image");

        MimeBodyPart part3 = (MimeBodyPart) multiPart.getBodyPart(2);
        assertThat(Part.ATTACHMENT.equalsIgnoreCase(part3.getDisposition())).isTrue();
        assertThat(part3.getFileName()).contains("my image");

    }

    @Test
    void sendHtmlMailWithAttachmentsTest() throws MessagingException, IOException {
        HtmlMailWithAttachments mailMessage= createHtmlMailWithAttachment();
        File img1=new File("src/test/resources/data/img1.jpg");
        File img2=new File("src/test/resources/data/img2.jpg");
        assertThat(img1).exists();
        assertThat(img2).exists();
        mailMessage.addAttachment("my image.jpg",img1);
        mailMessage.addAttachment("my image2.jpg",img2);
        mailService.sendEmail(mailMessage);

        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage.getSubject()).isEqualTo(mailMessage.getSubject());
        assertThat(receivedMessage.getAllRecipients().length).isEqualTo(1);
        Multipart multiPart = (Multipart) receivedMessage.getContent();
        MimeBodyPart part1=(MimeBodyPart)multiPart.getBodyPart(0);
        assertThat(GreenMailUtil.getBody(part1)).contains("text/html;charset=UTF-8");
        assertThat(GreenMailUtil.getBody(part1)).contains(mailMessage.getMessage());

        MimeBodyPart part2 = (MimeBodyPart) multiPart.getBodyPart(1);
        assertThat(Part.ATTACHMENT.equalsIgnoreCase(part2.getDisposition())).isTrue();
        assertThat(part2.getFileName()).contains("my image");

        MimeBodyPart part3 = (MimeBodyPart) multiPart.getBodyPart(2);
        assertThat(Part.ATTACHMENT.equalsIgnoreCase(part3.getDisposition())).isTrue();
        assertThat(part3.getFileName()).contains("my image");

    }

    public static DefaultMailTemplateWithAttachments createHtmlMailWithAttachment(){
        return
                new DefaultMailTemplateWithAttachments("alirezaalijani.ir@gmail.com","alirezatestmail@gmail.com",
                        "Html Mail Subject","My mail text is something about java","https://alirezaalijani.ir", TemplateType.Blue,
                        "Title Of My Mail","https://alirezaalijani.ir","View My Site","Alijani","https://alirezaalijani.ir");
    }

    public static TextMailWithAttachments createTextMailWithAttachment(){
        return
                new TextMailWithAttachments("alirezaalijani.ir@gmail.com","alirezatestmail@gmail.com",
                        "Text Mail Subject","My mail text is something about java","https://alirezaalijani.ir");
    }
    public static DefaultMailTemplate createHtmlMail(){
        return
                new DefaultMailTemplate("alirezaalijani.ir@gmail.com","alirezatestmail@gmail.com",
                        "Html Mail Subject","My mail text is something about java","https://alirezaalijani.ir", TemplateType.Blue,
                        "Title Of My Mail","https://alirezaalijani.ir","View My Site","Alijani","https://alirezaalijani.ir");
    }
    public static TextMail createTextMail(){
        return
                new TextMail("alirezaalijani.ir@gmail.com","alirezatestmail@gmail.com",
                        "Text Mail Subject","My mail text is something about java","https://alirezaalijani.ir");
    }
}