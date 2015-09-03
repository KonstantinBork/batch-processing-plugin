<%--
  Created by IntelliJ IDEA.
  User: konstantin
  Date: 03.09.15
  Time: 10:15
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Batch Input</title>
</head>

<body>

    <div id="headPicture">
        <g:img dir="images" file="grails_logo.png"/>
    </div>

    <div id="register">
        <h3>Register a new task</h3>
        <span id="selectTaskText">Select a task</span>
        <g:uploadForm action="registerTask" id="registerForm">
            <g:select name="taskName" from="${jobNames}"/>
            <input name="file" type="file"/>
            <input value="Submit" type="submit"/>
        </g:uploadForm>
    </div>

    <div id="running">
        <h3>Currently running</h3>
        <g:each var="taskName" in="${executions}">
            <g:if test="${taskName.value}">
                <!-- <strong>${taskName.key}</strong></br> -->
                <g:each var="id" in="${taskName.value}">
                    <div id="job${id}">
                        ${taskName.key} no. ${id}&nbsp;-&nbsp;
                        <g:link action="getStatus" params="[execId: id]">Show status</g:link> &nbsp;
                        <g:link action="stopTask" params="[execId: id]">Stop execution</g:link>
                    </div>
                </g:each>
            </g:if>
        </g:each>
    </div>

</body>
</html>