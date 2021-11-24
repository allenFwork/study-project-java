package com.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
public class UpLoadController {

    /**
     * 这里的处理方法中必须添加 @RequestPart("fileName") 这个注解
     * @param multipartFile
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(@RequestPart("fileName") MultipartFile multipartFile) {
        System.out.println("UpLoadController upload(MultipartFile multipartFile) ... ");
        // 将上传的文件下载
        OutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("d:/download.txt"));
            FileCopyUtils.copy(multipartFile.getInputStream(), fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 这里的处理方法中必须添加 @RequestPart("fileName") 这个注解
     * @param multipartFile
     */
    @RequestMapping(value = "/uploadByServlet3", method = RequestMethod.POST)
    public void uploadByServlet3(MultipartFile multipartFile) {
        System.out.println("UpLoadController uploadByServlet3(MultipartFile multipartFile) ... ");
        // 将上传的文件下载
        OutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(new File("d:/download.txt"));
            FileCopyUtils.copy(multipartFile.getInputStream(), fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
