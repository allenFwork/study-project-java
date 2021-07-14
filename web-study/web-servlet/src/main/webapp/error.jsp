<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
    <head>
        <title>错误提示</title>
    </head>
    <body style="font-size: 24px">
        <%=request.getAttribute("err_msg") == null ? "" : request.getAttribute("err_msg") %><br/>
    </body>
</html>
