package com.study.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * 计数监听：监听访问次数
 */
public class CounterListener implements HttpSessionListener {

    private int count = 0;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        count++;
        HttpSession session = httpSessionEvent.getSession();
        ServletContext servletContext = session.getServletContext();
        servletContext.setAttribute("count", count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        count--;
        HttpSession session = httpSessionEvent.getSession();
        ServletContext servletContext = session.getServletContext();
        servletContext.setAttribute("count", count);
    }

}
