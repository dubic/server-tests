/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.services;

import com.prisa.servertest.dto.TTool;
import com.prisa.servertest.engines.CommandEngine;
import com.prisa.servertest.engines.CustomScriptEngine;
import com.prisa.servertest.engines.RESTEngine;
import com.prisa.servertest.engines.RuntimeEngine;
import com.prisa.servertest.entities.ToolItem;
import com.prisa.servertest.enums.TestType;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author DUBIC
 */
@Named("testService")
public class TestService {

    private static final Logger log = Logger.getLogger(TestService.class);
@Inject private Database db;
    public TestService() {
    }

    public void runTest(TTool tParam) {
        log.info("running test - "+tParam);
        RuntimeEngine runtimeEngine = null;
        if(tParam.getType() == TestType.COMMAND){
            runtimeEngine = new CommandEngine();
        }else if(tParam.getType() == TestType.CUSTOM_SCRIPT){
            runtimeEngine = new CustomScriptEngine();
        }else if(tParam.getType() == TestType.REST){
            runtimeEngine = new RESTEngine();
        }
        runtimeEngine.executeTest(tParam);
    }

    public void saveTestTool(ToolItem tool) throws Exception{
        log.debug("saving test tool - "+tool.toString());
        db.merge(tool);
    }
    
    public void deleteTestTool(ToolItem tool) throws Exception{
        log.debug("delete test tool - "+tool.toString());
        db.delete(tool);
    }
    
    public void loadTestTools() throws Exception{
//        log.debug("saving test tool - "+tool.toString());
//        db.merge(tool);
        String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("load test tools for - "+userId);
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (int i = 0; i < authorities.toArray().length; i++) {
            
            
        }
        
    }
}
