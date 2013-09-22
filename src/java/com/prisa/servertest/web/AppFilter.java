/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.web;

import com.prisa.servertest.services.Database;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author DUBIC
 */
@Named
public class AppFilter implements Filter {
     private static final Logger log = Logger.getLogger(AppFilter.class);
    private boolean adminConfigured = false;
    @Inject private Database db;
    
    public AppFilter() {
    }
    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain)throws IOException, ServletException {
      if(!this.adminConfigured){
            //no admin, go to admin creation page
          request.getRequestDispatcher("adminCreationPage").forward(request, response);
        }else{
            chain.doFilter(request, response);
        }
    }

    /**
     * Destroy method for this filter
     */
    @Override
    public void destroy() {        
    }

    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {        
        log.debug("Application Filter initializing...........");
        log.debug("checking if admin account has been created");
        if(db.getAdmin() == null){
            this.adminConfigured = false;
        }else{
            this.adminConfigured = true;
        }
    }
}
