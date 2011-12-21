package cmg.org.monitor.services.email;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import cmg.org.monitor.common.Constant;
import cmg.org.monitor.dao.impl.AlertDaoJDOImpl;
import cmg.org.monitor.exception.MonitorException;
import cmg.org.monitor.ext.model.Component;
import cmg.org.monitor.ext.model.shared.SystemDto;
import cmg.org.monitor.util.shared.Appforyourdomain;
import cmg.org.monitor.util.shared.EmailDomainClientApps;
import cmg.org.monitor.util.shared.MonitorConstant;

import com.google.gdata.util.ServiceException;
import com.google.gdata.util.ServiceForbiddenException;

/**
 * @author lamphan
 * @version 1.0
 */
public class MailService {

	private static String rfcTxt = "Message-ID: <c8acb6980707161012i5d395392p5a6d8d14a8582613@mail."
			+ "gmail.com>\r\n"
			+ "From: \"System Monitor\" <adminmonitor@c-mg.com>\r\n";
	private static String rfcTxt_1 = "To: \"Monitor User\" <";
	private static String rfcTxt_2 = "@c-mg.com>\r\n";
	private static String RFC_TAIL =
			"Subject: Subject \r\n"
			+ "MIME-Version: 1.0\r\n"
			+ "Content-Type: text/plain; charset=ISO-8859-1; format=flowed\r\n"
			+ "Content-Transfer-Encoding: 7bit\r\n"
			+ "Content-Disposition: inline\r\n"
			+ "Delivered-To: admin@domain.com\r\n" + "\r\n";

	private static String firstRfcTxt = "Received: by 10.143.160.15 with HTTP;";

	/** Alert name */
	public static String ALERT_NAME = " alert report ";

	/** Declare email symbol */
	public static char EMAIL_SYMBOL = '@';

	/** Get log instance */
	private static final Logger logger = Logger.getLogger(AlertDaoJDOImpl.class
			.getName());

	public static String buildDateRfc() {
		Date now = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				Constant.DATE_EMAIL_FORMAT);

		String formatDateStr = formatter.format(now);

		String sentDate = "Date: " + formatDateStr + "\r\n";
		StringBuffer headerRfc = new StringBuffer();
		headerRfc.append(firstRfcTxt).append(formatDateStr).append("(PDT)\r\n")
				.append(sentDate);
		return headerRfc.toString();
	}

	/**
	 * Send alert email function.
	 * 
	 * @param systemDto
	 *            Data transfer object.
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public void sendAlertMail(SystemDto systemDto, String messageError)
			throws Exception, ServiceForbiddenException {
		String emailID = null;
		try {
			Appforyourdomain client = new Appforyourdomain(
					MonitorConstant.ADMIN_EMAIL_DotCom,
					MonitorConstant.ADMIN_PASSWORD_DotCom, MonitorConstant.DOMAIN_DotCom);
			String emailGroup = systemDto.getGroupEmail();
			String[] emailAddresses = client.listAllUser(emailGroup);

			String contentRtc;

			for (String user : emailAddresses) {
				for (int i = 0; i < user.length(); i++) {
					if (user.charAt(i) == EMAIL_SYMBOL) {
						emailID = user.substring(0, i);
						break;
					}
				}
				contentRtc = 
						buildDateRfc() + rfcTxt +rfcTxt_1 + emailID + 
						rfcTxt_2 + RFC_TAIL + messageError + "\r\n";
				new EmailDomainClientApps(MonitorConstant.ADMIN_EMAIL_ID,
						MonitorConstant.ADMIN_PASSWORD_DotCom, MonitorConstant.DOMAIN_DotCom,
						emailID, contentRtc);

			}
		} catch (ServiceForbiddenException sfe) {
			logger.log(Level.SEVERE, "Email to :" + emailID
					+ " has error due to :" + sfe.getCause().getMessage());
			throw sfe;
		} catch (Throwable t) {
			logger.log(Level.SEVERE, "Email to :" + emailID
					+ " has exception due to :" + t.getCause().getMessage());
		}
	}

	/**
	 * Send alert email function with parameters.<br>
	 * 
	 * @param systemDto
	 *            Data transfer object.
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public void sendAlertMail(Component component, String message,
			SystemDto systemDto) throws MonitorException, ServiceException,
			IOException {
		String emailID = null;
		try {
			Appforyourdomain client = new Appforyourdomain(
					MonitorConstant.ADMIN_EMAIL_DotCom,
					MonitorConstant.ADMIN_PASSWORD_DotCom, MonitorConstant.DOMAIN_DotCom);
			String emailGroup = systemDto.getGroupEmail();
			String[] emailAddresses;
			emailAddresses = client.listAllUser(emailGroup);

			String contentEmail = null;
			for (String user : emailAddresses) {
				for (int i = 0; i < user.length(); i++) {
					if (user.charAt(i) == EMAIL_SYMBOL) {
						emailID = user.substring(0, i);
						break;
					}
				}

				// Alert email with message
				if (component == null)
					contentEmail = buildDateRfc()  + rfcTxt +rfcTxt_1 + emailID + 
							rfcTxt_2 + RFC_TAIL  + message;
				else {
					String descriptionError = component.getError().equals("")? message:component.getError();
					// Alert email with component
					contentEmail = buildDateRfc() +  rfcTxt +rfcTxt_1 + emailID + 
							rfcTxt_2 + RFC_TAIL 
							+ descriptionError;
				}
				new EmailDomainClientApps(MonitorConstant.ADMIN_EMAIL_ID,
						MonitorConstant.ADMIN_PASSWORD_DotCom, MonitorConstant.DOMAIN_DotCom,
						emailID, contentEmail);
			}
		} catch (ServiceException se) {
			logger.log(Level.SEVERE, se.getCause().getMessage());
			throw se;
		} catch (IOException ioe) {
			logger.log(Level.SEVERE, ioe.getCause().getMessage());
			throw ioe;
		} catch (MonitorException me) {
			logger.log(Level.SEVERE, me.getCause().getMessage());
			throw me;
		}
	}
}
