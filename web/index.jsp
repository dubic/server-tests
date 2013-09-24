<%-- 
    Document   : index
    Created on : Sep 19, 2013, 9:01:33 PM
    Author     : DUBIC
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.prisa.servertest.entities.TestParam"%>
<%@page import="java.util.List"%>
<%@page import="com.prisa.servertest.services.TestService"%>
<%@page import="com.prisa.servertest.enums.TestType"%>
<%@page import="com.prisa.servertest.dto.TTool"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test Page</title>
        <script src="scripts/jquery-1.8.0.min.js"></script>
        <script src="scripts/application.js"></script>
        <script src="scripts/atmoshere.js"></script>
        
    </head>
    <body>
        <h3>Hello Test</h3>
        <div id="content"></div>
    </body>
</html>
<%
    ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
    TestService ce = ctx.getBean(TestService.class);
    TTool tt = new  TTool();
tt.setName("test jsp");
tt.setUri("C:\\Program Files\\java\\jre7\\bin\\java");
  tt.setType(TestType.COMMAND);
  List<TestParam> params = new ArrayList<TestParam>();
  TestParam tp = new TestParam();
  tp.setName("help");
  tp.setValue("-h");
  params.add(tp);
  tt.setParams(params);
    ce.runTest(tt);
%>
