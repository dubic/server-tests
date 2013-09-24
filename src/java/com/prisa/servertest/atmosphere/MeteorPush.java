/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.atmosphere;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.atmosphere.config.service.MeteorService;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResourceEventListener;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.Meteor;

/**
 *
 * @author user
 */
@MeteorService
public class MeteorPush extends HttpServlet {
private Logger log = Logger.getLogger(getClass());
    public MeteorPush() {
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        System.out.println("------------------------------------GET recieved--------------------------------------------");
        Meteor m = Meteor.build(req);

//        addAtmosphereResourceEventListener(m);
//        m.addListener(new WebSocketEventListenerAdapter());

        
        res.setContentType("text/html;charset=ISO-8859-1");
        //        System.out.println("Meteor built");
        String broadcasterId = req.getParameter("id");
        Broadcaster b = createBroadcaster(broadcasterId);
        m.setBroadcaster(b);  // performs broadcaster.addAtmosphereResource
        
        String header = req.getHeader("X-Atmosphere-Transport");
        if ((header != null) && (header.equalsIgnoreCase("long-polling"))) {
            req.setAttribute("org.atmosphere.resumeOnBroadcast", Boolean.TRUE);
            m.suspend(-1L);
        } else {
            m.suspend(-1L);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        log.debug("POST - client push not yet implemented in server_push 1.0");
    }

    Broadcaster createBroadcaster(String broadcasterId) {
        System.out.println("bid - "+broadcasterId);
        Broadcaster b = BroadcasterFactory.getDefault().lookup(broadcasterId, true);
        return b;
    }

    private void addAtmosphereResourceEventListener(Meteor m) {
                m.addListener(new AtmosphereResourceEventListener() {

            public void onSuspend(AtmosphereResourceEvent are) {
                System.out.println("suspended >> broadcaster ID >> "+are.broadcaster().getID());
                
            }

            public void onResume(AtmosphereResourceEvent are) {
                System.out.println("resumed >> broadcaster ID >> "+are.broadcaster().getID());
            }

            public void onDisconnect(AtmosphereResourceEvent are) {
               System.out.println("disconnected >> broadcaster ID >> "+are.broadcaster().getID());
            }

            public void onBroadcast(AtmosphereResourceEvent are) {
                System.out.println("braodcast call >> broadcaster ID >> "+are.broadcaster().getID());
            }

            public void onThrowable(AtmosphereResourceEvent are) {
               System.out.println("exception >> broadcaster ID >> "+are.broadcaster().getID());
            }
        });
    }
}
