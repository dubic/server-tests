/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.dto;

import com.google.gson.Gson;
import com.prisa.servertest.entities.TestParam;
import com.prisa.servertest.enums.TestType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DUBIC
 */
public class TTool {
    private String name;
    private TestType type;
    private String uri;
    private List<TestParam> params = new ArrayList<TestParam>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestType getType() {
        return type;
    }

    public void setType(TestType type) {
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<TestParam> getParams() {
        return params;
    }

    public void setParams(List<TestParam> params) {
        this.params = params;
    }

    
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
    
    
    
    
}
