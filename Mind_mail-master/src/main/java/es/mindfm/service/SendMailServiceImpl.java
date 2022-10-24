package es.mindfm.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import es.mindfm.template.model.CustomerInfo;
import es.mindfm.template.model.EventInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class SendMailServiceImpl implements SendMailService {

    private static final String EMAIL_TEXT_TEMPLATE_NAME = "text/email-text";

    private static final String from = "pruebas@mindfm.es";


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Autowired
    private TemplateEngine textTemplateEngine;

    @Autowired
    private TemplateEngine stringTemplateEngine;

    @Override
    public void sendSimpleMail(String to, String title, String content) {

        // Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("name", to);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
        try {
            // Prepare message using a Spring helper
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.toString());
            message.setSubject("Example plain TEXT email");
            message.setFrom(from);
            message.setText("PRUEBA 1", true);
            message.setTo(to);

            // Send email
            this.mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMailTemplate(String recipientName, String title, String templateName, CustomerInfo customerInfo) {
// Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("name", recipientName);
        ctx.setVariable("informalName", customerInfo.getCusName());
        ctx.setVariable("address", customerInfo.getCusAddress());
        ctx.setVariable("subscriptionDate", new Date());

        try {
            // Prepare message using a Spring helper
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject("Example HTML email (simple)");
            message.setFrom(from);
            message.setTo(recipientName);

            // Create the HTML body using Thymeleaf
            final String htmlContent = this.htmlTemplateEngine.process(templateName, ctx);
            message.setText(htmlContent, true /* isHtml */);

            // Send email
            this.mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMailTemplate(String recipientName, String title, String templateName, CustomerInfo customerInfo, EventInfo event){
        // Prepare the evaluation context
        final Context ctx = new Context();

        try {
        // set values to the mail template for event
        ctx.setVariable("name", event.getName());
        ctx.setVariable("eventFromName", event.getOwner());
        ctx.setVariable("emailTo", event.getTo());
        ctx.setVariable("eventDate", event.getDateSpa());
        ctx.setVariable("eventStart",event.getInitTime());
        ctx.setVariable("eventFinish",event.getEndTime());
        ctx.setVariable("eventAddress", event.getAddress());
        ctx.setVariable("urlEvent", event.getUrlEvent());

        // set values to the mail template for customerInfo
        ctx.setVariable("informalName", event.getName());
        ctx.setVariable("address", customerInfo.getCusAddress());
        ctx.setVariable("subscriptionDate", new Date());


            // Prepare message using a Spring helper
            final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject("Example HTML email (simple)");
            message.setFrom(from);
            message.setTo(recipientName);

            // Create the HTML body using Thymeleaf
            final String htmlContent = this.htmlTemplateEngine.process(templateName, ctx);
            message.setText(htmlContent, true /* isHtml */);

            // Send email
            this.mailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



