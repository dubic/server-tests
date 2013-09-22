/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.services;

import com.prisa.servertest.entities.User;
import java.util.List;

/**
 *
 * @author DUBIC
 */
public interface UserService {
    public  User createUser(User paramUser)
    throws Exception;

//  public  User login(String paramString1, String paramString2)
//    throws Exception;

  public  List<User> findAllUsers()
    throws Exception;

  public  User findUser(Long paramLong)
    throws Exception;

  public  void logout(Long paramLong)
    throws Exception;

  public  User updateUser(User paramUser)
    throws Exception;

  public  User activateUser(Long paramLong)
    throws Exception;

  public  User reactivateUser(Long paramLong)
    throws Exception;

  public  User deactivateUser(Long paramLong)
    throws Exception;

//  public  User registerOnlineUser(User paramUser)
//    throws Exception;

    public User findUserByUsernamePassword(String name, String password);
}
