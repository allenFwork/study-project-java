<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>生成的验证码</title>
    <style type="text/css">
        .s1 {
            cursor:pointer;
        }
    </style>
</head>
<body>
    验证码：<input name="validateCode">
    <img src="validateCodeServlet" onclick="this.src = 'validateCodeServlet?' + Math.random();" class="s1" title="点击更换"/>
</body>
</html>
