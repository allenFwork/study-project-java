package com.study.http.message;

import com.study.entity.Person;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Person 自描述信息处理
 */
public class PropertiesPersonHttpMessageConverter extends AbstractHttpMessageConverter<Person> {

    public PropertiesPersonHttpMessageConverter() {
        super(MediaType.valueOf("application/properties+person"));
        setDefaultCharset(Charset.forName("UTF-8"));
    }

    /**
     * 判断是否支持当前的POJO类型
     */
    @Override
    protected boolean supports(Class<?> clazz) {
        // 判断传进来的类型是否是Person的子类
        return clazz.isAssignableFrom(Person.class);
    }

    /**
     * 将请求内容中的 Properties内容转化成为 Person
     * @param clazz
     * @param httpInputMessage
     * @return
     * @throws IOException
     * @throws HttpMessageNotReadableException
     */
    @Override
    protected Person readInternal(Class<? extends Person> clazz, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        /**
         * person.id = 1
         * person.name = superman
         */
        InputStream inputStream = httpInputMessage.getBody();
        Properties properties = new Properties();
        // 将请求中的内容转化为Properties
        properties.load(new InputStreamReader(inputStream, getDefaultCharset()));
        Person person = new Person();
        person.setId(Long.valueOf(properties.getProperty("person.id")));
        person.setName(properties.getProperty("person.name"));
        return person;
    }

    /**
     * 将POJO的内容序列化为文本内容(Properties格式)，最终输出到HTTP的响应中
     * @param person
     * @param httpOutputMessage
     * @throws IOException
     * @throws HttpMessageNotWritableException
     */
    @Override
    protected void writeInternal(Person person, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        Properties properties = new Properties();
        properties.setProperty("person.id", String.valueOf(person.getId()));
        properties.setProperty("person.name", person.getName());
        OutputStream outputStream = httpOutputMessage.getBody();
        properties.store(new OutputStreamWriter(outputStream, getDefaultCharset()),"written by web service");
    }
}