package doan2020.SportTournamentSupportSystem.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;


@Service
public class SendingMailService {
	
	@Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private String port;
    @Value("${spring.mail.username}")
    private String email;
    @Value("${spring.mail.password}")
    private String password;
    
	private final Configuration templates;

	@Autowired
	SendingMailService(Configuration templates) {
		this.templates = templates;
	}

	public boolean sendVerificationMail(String toEmail, String verificationCode, String username, String domain) {
		String subject = "Please verify your email";
		String body = "";
		try {
			Template t = templates.getTemplate("email-verification.ftl");
			Map<String, String> map = new HashMap<>();
			map.put("USERNAME",username);
			map.put("DOMAIN",domain);
			map.put("VERIFICATION_URL",verificationCode);
			body = FreeMarkerTemplateUtils.processTemplateIntoString(t, map);
		} catch (Exception ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}
		return sendMail(toEmail, subject, body);
	}

	private boolean sendMail(String toEmail, String subject, String body) {
		try {
			Properties props = System.getProperties();
			props.put("mail.transport.protocol", "smtp");
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props);
			session.setDebug(true);

			MimeMessage msg = new MimeMessage(session);
//			message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress("received_mail@domain.com")});
			msg.setFrom(new InternetAddress(email));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			msg.setSubject(subject);
			msg.setContent(body, "text/html");

			Transport transport = session.getTransport();
			transport.connect(host, email,password);
			transport.sendMessage(msg, msg.getAllRecipients());
			return true;
		} catch (Exception ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
		}

		return false;
	}
}
