/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author DUBIC
 */
public class TestUtils {
    public static String hash(String toHash){
        return DigestUtils.md5Hex(toHash);
    }
}
