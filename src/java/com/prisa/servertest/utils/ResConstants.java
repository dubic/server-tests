/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.utils;

import java.util.ResourceBundle;

/**
 *
 * @author DUBIC
 */
public class ResConstants {
    public static final String ADMIN_CREATE_PAGE  = "adminCreationPage";
    public static final String PUSH_URL = "http://localhost:2020/server-test/serverpush";
    public static final String MAIL_SERVER;
    public static final String MAIL_SENDER_NAME;
    public static String MAIL_SERVER_PORT;
    public static String ACTIVATION_MAIL_SUBJECT;
    static {
        MAIL_SERVER = ResourceBundle.getBundle("resources.mail").getString("prisa.mail.server");
        MAIL_SENDER_NAME = ResourceBundle.getBundle("resources.mail").getString("prisa.mail.sender");
        MAIL_SERVER_PORT = ResourceBundle.getBundle("resources.mail").getString("prisa.mail.port");
        ACTIVATION_MAIL_SUBJECT = ResourceBundle.getBundle("resources.mail").getString("activation.mail.subject");
    }
    
}
