/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.engines;

import com.prisa.servertest.dto.TTool;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;

/**
 *
 * @author DUBIC
 */
public class CommandEngine implements RuntimeEngine{
    private static final Logger log = Logger.getLogger(CommandEngine.class);
    private TTool tParam;
    private Thread testThread;
    
    @Override
    public Future executeTest(TTool tParam) {
        log.debug("initiating command test - "+tParam);
         this.tParam = tParam;
       testThread = new Thread(this);
       testThread.start();
        return null;
    }

    @Override
    public void run() {
        log.debug("executing command test - "+tParam);
        ProcessBuilder pb = new ProcessBuilder(tParam.getUri());
        for (Object object : tParam.getParams()) {
            pb.command((String)object);
        }
        try {
            Process cProcess = pb.start();
            InputStream inputStream = cProcess.getInputStream();
            InputStream errorStream = cProcess.getErrorStream();
            String data = readStream(inputStream, errorStream);
            updateResult(data);
        } catch (IOException ex) {
            
        }
    }

    private String readStream(InputStream inputStream, InputStream errorStream) throws IOException {
        log.debug("reading data from streams");
        String line = null;
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = reader.readLine()) != null) {            
            buffer.append(line);
        }
        return buffer.toString();
    }

    @Override
    public void stop() throws Exception {
        if (testThread.isAlive()) {
            testThread.interrupt();
            log.debug("command test engine destroyed");
        }
    }

    @Override
    public void updateResult(String data) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  
    
}
