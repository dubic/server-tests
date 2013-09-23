/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testy;

import com.google.gson.Gson;
import com.prisa.servertest.entities.ToolItem;
import com.prisa.servertest.enums.TestType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DUBIC
 */
public class Test {
    public static void main(String[] djk){
//        runCmd();
        postTest();
    }
    private static String readStream(InputStream inputStream, InputStream errorStream) throws IOException {
        System.out.println("reading data from streams");
        String line = null;
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = reader.readLine()) != null) {            
            buffer.append(line);
        }
        BufferedReader ereader = new BufferedReader(new InputStreamReader(errorStream));
        while ((line = ereader.readLine()) != null) {            
            buffer.append(line);
        }
        return buffer.toString();
    }

    private static void runCmd() {
        //        System.out.println("enc - "+DigestUtils.md5Hex("dubicdcamic4602"));
//         log.debug("executing command test - "+tParam);
        ProcessBuilder pb = new ProcessBuilder("C:\\Program Files\\java\\jre7\\bin\\java","-version");
//        for (Object object : tParam.getParams()) {
//            pb.command((String)object);
//        }
        try {
            Process cProcess = pb.start();
            InputStream inputStream = cProcess.getInputStream();
            InputStream errorStream = cProcess.getErrorStream();
            String data = readStream(inputStream, errorStream);
            System.out.println("data - "+data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void postTest() {
        try {
            HttpURLConnection httpconn = getHttpconn("http://localhost:2020/server_push/serverpush");
            httpconn.setRequestMethod("POST");//RequestProperty("Content-Type", "application/octet-stream");
            OutputStream os = httpconn.getOutputStream();
            ToolItem tt = new ToolItem();
            tt.setDescription("a test tool");
            tt.setId(Long.MIN_VALUE);
            tt.setName("test item");
            tt.setType(TestType.REST);
            os.write(("data="+new Gson().toJson(tt)).getBytes());
    //        os.write("id=dubic".getBytes());
    //        os.write(("data="+new Gson().toJson(new URL(url))).getBytes());
            InputStream is = httpconn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String resp = br.readLine();
            os.close();
            is.close();
        } catch (Exception ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
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
