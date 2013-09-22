/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.prisa.servertest.entities;

import com.google.gson.Gson;
import com.prisa.servertest.enums.TestType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author DUBIC
 */
@Entity
@Table(name = "st_test_tool")
public class TestTool implements Serializable {

    private Long id;
    private String name;
    private String description;
    private TestType type;
    private List<TestParam> testParams = new ArrayList<TestParam>();
    private Date created = new Date();
    private Date updated = new Date();

    public TestTool() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    public TestType getType() {
        return type;
    }

    public void setType(TestType type) {
        this.type = type;
    }

    @OneToMany(cascade= CascadeType.ALL)
    public List<TestParam> getTestParams() {
        return testParams;
    }

    public void setTestParams(List<TestParam> testParams) {
        this.testParams = testParams;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
