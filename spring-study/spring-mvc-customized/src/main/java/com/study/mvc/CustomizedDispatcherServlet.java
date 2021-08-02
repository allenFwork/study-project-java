package com.study.mvc;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class CustomizedDispatcherServlet extends HttpServlet {

    private WebApplicationContext context;
    private MvcBeanFactory beanFactory;
    final ParameterNameDiscoverer parameterUtil = new LocalVariableTableParameterNameDiscoverer();
    private Configuration freemarkeConfig;

    @Override
    public void init() throws ServletException {
        context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        beanFactory = new MvcBeanFactory(context);
        Configuration freemarkeConfig = null;
        try {
            freemarkeConfig = context.getBean(Configuration.class);
        } catch (NoSuchBeanDefinitionException e) {
        }
        if (freemarkeConfig == null) {
            freemarkeConfig = new Configuration(Configuration.VERSION_2_3_23);
            freemarkeConfig.setDefaultEncoding("UTF-8");
            freemarkeConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            try {
                freemarkeConfig.setDirectoryForTemplateLoading(new File(getServletContext().getRealPath("/ftl/")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.freemarkeConfig = freemarkeConfig;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        doHandler(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        doHandler(req, resp);
    }

    // 需要添加处理静态文件
    public void doHandler(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getServletPath();
        if (uri.equals("/favicon.ico")) {
            return;
        }
        MvcBeanFactory.MvcBean mvcBean = beanFactory.getMvcBean(uri);

        if (mvcBean == null) {
            throw new IllegalArgumentException(String.format("not found %s mapping", uri));
        }
        // 处理请求参数
        Object[] args = buildPrams(mvcBean, request, response);
        try {
            Object result = mvcBean.run(args);
            processResult(result, response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processResult(Object result, HttpServletResponse resp) throws IOException, TemplateException {
        if (result instanceof FreemarkeView) {
            FreemarkeView fview = (FreemarkeView) result;
            Template temp = freemarkeConfig.getTemplate(fview.getFtlPath());
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            resp.setContentType("text/html; charset=utf-8");
            resp.setCharacterEncoding("utf-8");
            resp.setStatus(200);
            temp.process(fview.getModels(), resp.getWriter());
        }
        // JSTL VIEW
        // JSON VIEW
    }

    private Object[] buildPrams(MvcBeanFactory.MvcBean mvcBean, HttpServletRequest request, HttpServletResponse response) {
        Method method = mvcBean.getTargetMethod();
        List<String> paramNames = Arrays.asList(parameterUtil.getParameterNames(method));
        Class<?>[] paramTypes = method.getParameterTypes(); //反射
        // a.b.c
        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramNames.size(); i++) {
            if (paramTypes[i].isAssignableFrom(HttpServletRequest.class)) {
                args[i] = request;
            } else if (paramTypes[i].isAssignableFrom(HttpServletResponse.class)) {
                args[i] = response;
            } else {
                if (request.getParameter(paramNames.get(i)) == null) {
                    args[i] = null;
                } else {
                    args[i] = convert(request.getParameter(paramNames.get(i)), paramTypes[i]);
                }
            }
        }
        return args;
    }

    // 转换类型
    public <T> T convert(String val, Class<T> targetClass) {
        Object result = null;
        if (val == null) {
            return null;
        } else if (Integer.class.equals(targetClass)) {
            result = Integer.parseInt(val.toString());
        } else if (Long.class.equals(targetClass)) {
            result = Long.parseLong(val.toString());
        } else if (Date.class.equals(targetClass)) {
            try {
                result = new SimpleDateFormat("").parse(val);
            } catch (ParseException e) {
                throw new IllegalArgumentException("date format Illegal must be 'yyyy-MM-dd HH:mm:ss'");
            }
        } else if (String.class.equals(targetClass)) {
            result = val;
        }
        // TODO Serializable 进行自动封装
        else {
            System.err.println(String.format("not suport param type %s", targetClass.getName()));
        }
        return (T) result;
    }


}
