/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.prisa.servertest.atmosphere;

import com.prisa.servertest.utils.ResConstants;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.inject.Named;

/**
 *
 * @author dubic
 */
@Named("pushService")
public class PushService {
    public PushService(){
    }
    
     public static void push() throws MalformedURLException, IOException{
        HttpURLConnection httpconn = getHttpconn(ResConstants.PUSH_URL);
        httpconn.setRequestMethod("POST");//RequestProperty("Content-Type", "application/octet-stream");
        OutputStream os = httpconn.getOutputStream();
//        os.write("id=dubic".getBytes());
//        os.write(("data="+new Gson().toJson(new URL(url))).getBytes());
        os.close();
    }

    private static HttpURLConnection getHttpconn(String url) throws MalformedURLException, IOException {
        URL servletURL = new URL(url);
        HttpURLConnection servletConnection = (HttpURLConnection) servletURL.openConnection();
        servletConnection.setDoOutput(true);
        servletConnection.setDoInput(true);
        servletConnection.setUseCaches(false);
        servletConnection.setDefaultUseCaches(false);
        return servletConnection;
    }
}
