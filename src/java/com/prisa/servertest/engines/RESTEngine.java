/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.engines;

import com.prisa.servertest.dto.TTool;
import java.util.concurrent.Future;

/**
 *
 * @author DUBIC
 */
public class RESTEngine implements RuntimeEngine{

    @Override
    public Future executeTest(TTool tParam) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateResult(String data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void stop() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
