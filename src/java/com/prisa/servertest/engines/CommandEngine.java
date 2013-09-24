/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.engines;

import com.prisa.servertest.atmosphere.PushService;
import com.prisa.servertest.dto.TTool;
import com.prisa.servertest.entities.TestParam;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;

/**
 *
 * @author DUBIC
 */
public class CommandEngine implements RuntimeEngine{
    private static final Logger log = Logger.getLogger(CommandEngine.class);
    private TTool tParam;
    private Thread testThread;
    private boolean updateReady = true;
    
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
//        ProcessBuilder pb = new ProcessBuilder(tParam.getUri());
        ProcessBuilder pb = new ProcessBuilder("C:\\Program Files\\java\\jre7\\bin\\java","-h");
//        for (TestParam param : tParam.getParams()) {
//            pb.command(param.getValue());
//        }
        try {
            Process cProcess = pb.start();
            InputStream inputStream = cProcess.getInputStream();
            InputStream errorStream = cProcess.getErrorStream();
            String data = readStream(inputStream, errorStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private String readStream(InputStream inputStream, InputStream errorStream) throws IOException {
        log.debug("reading data from streams");
        String line = null;
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = reader.readLine()) != null) {            
            buffer.append("input - ").append(line);
        }
        BufferedReader ereader = new BufferedReader(new InputStreamReader(errorStream));
        while ((line = ereader.readLine()) != null) {            
            buffer.append(line);
            while (this.updateReady) {                
                updateResult(line, "dubic");
                updateReady = false;
            }
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
    public void updateResult(String data, String bid) {
        try {
            //        Broadcaster b = BroadcasterFactory.getDefault().lookup(bid);
            //        b.broadcast(data);
                    PushService.push(data, bid);
                    log.debug("data pushed to >> "+bid);
        } catch (MalformedURLException ex) {
            java.util.logging.Logger.getLogger(CommandEngine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(CommandEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        updateReady = true;
    }

    @Override
    public boolean isUpdateReady() {
        return this.updateReady;
    }

  
    
}
