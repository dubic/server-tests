/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.services;

import com.prisa.servertest.entities.Email;
import com.prisa.servertest.entities.Role;
import com.prisa.servertest.entities.User;
import com.prisa.servertest.enums.RoleType;
import com.prisa.servertest.enums.UserState;
import com.prisa.servertest.enums.UserStatus;
import com.prisa.servertest.enums.UserType;
import com.prisa.servertest.utils.ResConstants;
import com.prisa.servertest.utils.TestUtils;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Query;
import javax.security.auth.login.CredentialNotFoundException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author DUBIC
 */
@Named("userService")
public class UserServiceBean implements UserService {
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(UserServiceBean.class);
    @Inject private Database db;

    @Override
    public User createUser(User user) throws Exception {
        user.setUserType(UserType.LOCAL_USER);
        user.setPassword(TestUtils.hash(user.getUsername()+user.getPassword()));
        User createdUser = (User) db.merge(user);
        //if user created is not a LOCAL user, send activation mail
        if(createdUser.getId() != null && createdUser.getUserType() != UserType.LOCAL_USER){
            queueActivationMail(createdUser);
        }
        return createdUser;
    }

//    @Override
//    public User login(String name, String password) throws Exception {
//        System.out.println(name + "   " + password);
//        Query query = super.getEntityManager().createQuery("SELECT u FROM User u WHERE u.name = :name AND u.password = :password");
//
//        query.setParameter("name", name);
//        query.setParameter("password", password);
//        User user = null;
//        User findEntity = (User) query.getSingleResult();
//        return findEntity;
//    }

    @Override
    public List<User> findAllUsers() throws Exception {
        return db.getAll(User.class);
    }

    protected Query getSearchCriteria(Serializable paramSerializable, String paramString, boolean paramBoolean) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User findUser(Long userId) throws Exception {
        return db.get(User.class, userId);
    }

    @Override
    public void logout(Long userId) throws Exception {
        User user = findUser(userId);
        user.setUserState(UserState.LOGGED_OUT);
        updateUser(user);
    }

    @Override
    public User updateUser(User user) throws Exception {
        return db.merge(user);
    }

    @Override
    public User activateUser(Long userId) throws Exception {
        User user = findUser(userId);
        user.setUserStatus(UserStatus.ACTIVATED);
        return updateUser(user);
    }

    @Override
    public User reactivateUser(Long userId) throws Exception {
        User user = findUser(userId);
        user.setUserStatus(UserStatus.REACTIVATED);
        return updateUser(user);
    }

    @Override
    public User deactivateUser(Long userId) throws Exception {
        User user = findUser(userId);
        user.setUserStatus(UserStatus.DEACTIVATED);
        return updateUser(user);
    }

//    public User registerOnlineUser(User user) throws Exception {
//        user.setUserType(UserType.ONLINE_USER);
//        User createdUser = (User) super.createEntity(user);
//        return createdUser;
//    }

    @Override
    public User findUserByUsernamePassword(String name, String password) throws AuthenticationException{
        try {
            return db.findUserByUsernamePassword(name, password);
        } catch (Exception ex) {
           throw new AuthenticationCredentialsNotFoundException("error in db");
        }
    }

    private void queueActivationMail(User user) throws Exception {
        log.info("queueing activation mail for - "+user.getFirstName());
        Email mail = new Email();
        mail.setBody(null);
        mail.setRecipient(user.getEmail());
        mail.setSender(ResConstants.MAIL_SENDER_NAME);
        mail.setSubject(ResConstants.ACTIVATION_MAIL_SUBJECT);
        db.persist(mail);
        log.info("mail queued for - "+user.getEmail());
    }
}
