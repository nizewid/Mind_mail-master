package es.mindfm.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import es.mindfm.template.model.CustomerInfo;
import es.mindfm.template.model.EventInfo;

public interface SendMailService {
	
	public void sendSimpleMail(String to, String title, String content);
	
	public void sendMailTemplate(String to, String title, String templateName, CustomerInfo customerInfo);

	public void sendMailTemplate(String to, String title, String templateName, CustomerInfo customerInfo, EventInfo event);

}
