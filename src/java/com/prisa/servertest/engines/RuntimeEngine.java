/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.engines;

import com.prisa.servertest.dto.TTool;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 *
 * @author DUBIC
 */
public interface RuntimeEngine extends Runnable {
    public Future executeTest(TTool tParam);
    public void updateResult(String data, String bid);
    public void stop() throws Exception;
    public boolean isUpdateReady();
}
