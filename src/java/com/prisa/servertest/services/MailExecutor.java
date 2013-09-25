/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prisa.servertest.services;

import com.prisa.servertest.entities.Email;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.mail.EmailException;
import org.springframework.scheduling.annotation.Scheduled;

/**
 *
 * @author dubic//user
 */
@Named
public class MailExecutor {
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(MailExecutor.class);
    @Inject private Database db;
    @Inject private MailService mailService;
    public MailExecutor(){
    }
    
    @Scheduled(fixedDelay=10000)
    public void sendMail(){
        List<Email> emails = db.getUnsentMails();
        for (Email email : emails) {
            try {
                mailService.sendMail(email);
                email.setSent(true);
                email.setSentDate(new Date());
            } catch (EmailException ex) {
                log.error("error occurred in sending mail - "+email.getSubject(), ex);
                email.setErrorMsg("mail send error - "+ex.getMessage());
            }
            finally{
                 try {
                    db.merge(email);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
