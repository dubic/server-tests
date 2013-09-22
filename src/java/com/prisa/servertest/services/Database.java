/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.services;


import java.util.List;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author DUBIC
 */
@Named
public class Database {

    private static final Logger log = Logger.getLogger(Database.class);
    @PersistenceContext
    private EntityManager em;

    public Database() {
    }

    @Transactional
    public <T> void persist(T t) throws Exception {
        log.debug("persist {}");
        em.persist(t);
        log.debug("persist completed...");
    }

    @Transactional
    public <T> T merge(T t) throws Exception {
        log.debug("merge {}");
        t = em.merge(t);
        log.debug("merge completed...");
        return t;
    }

    @Transactional
    public <T> void delete(T t) throws Exception {
        log.debug("delete {}");
        em.remove(t);
        log.debug("delete completed...");
    }

    
    public <T> T get(Class<T> tClass, Object t) throws Exception {
        log.debug("get {}");
        return em.find(tClass, t);
    }

    
    public <T> List<T> getAll(Class<T> tClass, int page, int size) throws Exception {
        log.debug("getAll {}");
        int len = page * size;
        int start = len - size + 1;
        log.debug("\nstart = " + start + "\nlen = " + len);
        return em.createQuery("SELECT t FROM " + tClass.getSimpleName() + " t", tClass)
                .setFirstResult(start).setMaxResults(len).getResultList();
    }

    
    public <T> List<T> getAll(Class<T> tClass) throws Exception {
        log.debug("getAll {}");
        return em.createQuery("SELECT t FROM " + tClass.getSimpleName() + " t", tClass)
                .getResultList();
    }
    
    public <T> T getAdmin(){
        throw new UnsupportedOperationException("remi give me users jar");
    }

//    public User findUserByEmailPasswordStatus(String email, String pword, Status status) throws Exception{
//        return em.createQuery("SELECT u FROM User u where u.email = :email AND u.password = :pword and u.status = :status",User.class)
//                .setParameter("pword", pword).setParameter("email", email)
//                .setParameter("status", status).getSingleResult();
//    }
//
//    public User findUserByEmail(String email) throws Exception{
//        log.debug("findByString("+email+")");
//        return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
//                .setParameter("email", email).getSingleResult();
//    }
//
//    public UserLoginAttempts getUserLoginAttempt(User user) throws Exception{
//        return em.createQuery("SELECT la FROM UserLoginAttempts la where la.user.id = :id", UserLoginAttempts.class)
//                .setParameter("id", user.getId()).getSingleResult();
//    }
}
