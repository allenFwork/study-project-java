package com.study.servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Cookie的使用
 */
public class CookieServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        String type = request.getParameter("type");
        if ("create".equals(type)) {
            // 创建 Cookie，空格不能出现在cookie的value值中，无效字符
            Cookie cookie1 = new Cookie("superman", "fly");
            Cookie cookie2 = new Cookie("batman", "fight");
            cookie1.setMaxAge(1000);
            // 添加 Cookie 到 response 中
            response.addCookie(cookie1);
            response.addCookie(cookie2);
            writer.print("<h1>cookie已经返回</h1>");
        } else if("find".equals(type)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    String name = cookie.getName();
                    String value = cookie.getValue();
                    writer.print("name=" + name + ", value=" + value);
                    writer.print("<br>");
                }
            } else {
                writer.print("没有 Cookie 信息");
            }
        }
        writer.close();
     }
}
