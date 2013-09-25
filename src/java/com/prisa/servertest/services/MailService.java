/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.services;

import com.prisa.servertest.engines.CommandEngine;
import com.prisa.servertest.entities.Email;
import com.prisa.servertest.utils.ResConstants;
import java.util.Map;
import java.util.StringTokenizer;
import javax.inject.Named;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

/**
 *
 * @author dubic//user
 */
@Named("mailService")
public class MailService {

    private static final Logger log = Logger.getLogger(MailService.class);

    public MailService() {
    }

    
    /**does the actual mail sending
     *
     * @param mailModel
     * @throws EmailException
     */
    public void sendMail(Email mailModel) throws EmailException {
        log.info("sending mail ..." + mailModel.getSubject());
        HtmlEmail email = new HtmlEmail();

        email.setHostName(ResConstants.MAIL_SERVER);
        email.setFrom(mailModel.getSender(), ResConstants.MAIL_SENDER_NAME);
        String addresses =mailModel.getRecipient();
        StringTokenizer st = new StringTokenizer(addresses, ",");
        for (int i = 0; st.hasMoreTokens(); i++) {
            String token = st.nextToken();
            if (i == 0) {
                email.addTo(token);
            } 
        }
        email.setSubject(mailModel.getSubject());
        email.setHtmlMsg(mailModel.getBody());
        email.setTLS(true);
email.setSmtpPort(Integer.parseInt(ResConstants.MAIL_SERVER_PORT));
        // send the email
        email.send();
        log.info("mail sent!! - " + mailModel.getSubject());
    }
    
    
}
