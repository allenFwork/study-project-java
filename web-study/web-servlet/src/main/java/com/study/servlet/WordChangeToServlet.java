package com.study.servlet;//package com.study.servlet;
//
//import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
//import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//
//public class WordChangeToServlet extends HttpServlet {
//
//    @Override
//    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        // 1) 加载word文档生成 XWPFDocument对象
////        InputStream inputStream = request.getInputStream();
//        File file = new File("Z:\\document\\保单查询页面模板（滴滴出行平台车辆订单乘客意外险）.docx");
//        InputStream inputStream = new InputStream(file);
//        XWPFDocument document = new XWPFDocument(inputStream);
//
//        // 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录)
////        File imageFolderFile = new File(filepath);
////        XHTMLOptions options = XHTMLOptions.create().URIResolver(new FileURIResolver(imageFolderFile));
////        options.setExtractor(new FileImageExtractor(imageFolderFile));
////        options.setIgnoreStylesIfUnused(false);
////        options.setFragment(true);
//
//        // 3) 将 XWPFDocument转换成XHTML
////        OutputStream out = new FileOutputStream(new File(filepath + htmlName));
////        XHTMLConverter.getInstance().convert(document, out, options);
//
//        //也可以使用字符数组流获取解析的内容
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        XHTMLConverter.getInstance().convert(document, baos, options);
//        String content = baos.toString();
//        System.out.println(content);
//        baos.close();
//
//    }
//}
