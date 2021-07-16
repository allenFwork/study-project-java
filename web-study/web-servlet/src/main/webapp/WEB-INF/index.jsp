<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1><%=request.getSession().getAttribute("userName") %></h1>
    当前共有<%=application.getAttribute("count").toString() %>人在线
</body>
</html>
