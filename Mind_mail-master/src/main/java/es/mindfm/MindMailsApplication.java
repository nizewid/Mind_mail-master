package es.mindfm;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.mindfm.template.model.EventInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import es.mindfm.service.SendMailService;
import es.mindfm.service.SendMailServiceImpl;
import es.mindfm.template.model.CustomerInfo;

@SpringBootApplication
public class MindMailsApplication implements CommandLineRunner {

	@Autowired
	ApplicationContext applicationContext;

	//private static String to = "pedro.gonzalez@mindfm.es";
	//private static String to = "lucia.suarezcoronas@mindfm.es";
	private static String to = "euroflores1@gmail.com";
	
	public static void main(String[] args) {
		SpringApplication.run(MindMailsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		SendMailService sendMailService = applicationContext.getBean(SendMailServiceImpl.class);
		testSendMailTemplate(sendMailService);
	}


		private void testSendMailTemplate(SendMailService sendMailService) throws IOException, ParseException {

		ObjectMapper om = new ObjectMapper();
		// Path to the json File.
		File jsonFile = new File("C:\\Mind\\Mind_mail-master\\src\\main\\resources\\files\\event.json");
		CustomerInfo customerInfo = new CustomerInfo();
		EventInfo event = om.readValue(jsonFile,EventInfo.class);
		// Example case send simple mail


		customerInfo.setCusName("Nombre Prueba");
		customerInfo.setCusAddress("Calle Profesor Potter, 51, Bajos, Puerta 1, Gijón (33394 - Asturias)");
		sendMailService.sendMailTemplate(to, "Test", "BASE_TEMPLATE", customerInfo,event);
		
	}

	private void testSendSimpleMail(SendMailService sendMailService) {
		sendMailService.sendSimpleMail(to, "Test Attachments", "EMAIL_TEMPLATE_01_EVENT");
	}


}
