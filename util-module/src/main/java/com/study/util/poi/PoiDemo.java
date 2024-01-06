package com.study.util.poi;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class PoiDemo {

    /**
     * 通过POI创建Excel文件并且写入文件内容
     *
     * @throws Exception
     */
    public static void write() throws Exception {
        // 在内存中创建一个Excel文件对象
        XSSFWorkbook excel = new XSSFWorkbook();
        // 创建Sheet页
        XSSFSheet sheet = excel.createSheet("sheet1(自定义sheet页名字)");

        // 在Sheet页中创建行，即创建行对象，行的编号从0开始：0表示第1行
        XSSFRow row1 = sheet.createRow(0);
        // 创建单元格并在单元格中设置值，单元格编号也是从0开始，1表示第2个单元格
        row1.createCell(0).setCellValue("姓名");
        row1.createCell(1).setCellValue("年龄");
        row1.createCell(2).setCellValue("城市");

        XSSFRow row2 = sheet.createRow(1);
        row2.createCell(0).setCellValue("张三");
        row2.createCell(1).setCellValue(24);
        row2.createCell(2).setCellValue("北京");

        XSSFRow row3 = sheet.createRow(2);
        row3.createCell(0).setCellValue("李四");
        row3.createCell(1).setCellValue(25);
        row3.createCell(2).setCellValue("上海");

        // 到此为止Excel的数据信息都存储在内存中，下面将内存中的数据写入到磁盘上
        FileOutputStream out = new FileOutputStream(new File("D:\\temp\\test.xlsx"));
        // 通过输出流将内存中的Excel文件写入到磁盘上
        excel.write(out);
        out.flush();

        // 关闭资源
        out.close();
        excel.close();
    }


    /**
     * 通过POI读取Excel文件的内容
     *
     * @throws Exception
     */
    public static void read() throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\temp\\test.xlsx"));

        // 读取磁盘上已经存在的Excel文件，在内存中创建一个Excel文件对象
        XSSFWorkbook excel = new XSSFWorkbook(fileInputStream);
        // 读取Excel文件中的第一个Sheet页
        XSSFSheet sheet = excel.getSheetAt(0);
        // excel.getSheet("xxx"); // 读取Excel文件中的第Sheet页名字为“xxx”的sheet页

        // 获取Sheet页中最后一行的行号，行号编码依旧是从0开始的
        int lastRowNum = sheet.getLastRowNum();

        // 从第二行开始读取（第一行是标题）
        for (int i = 1; i <= lastRowNum; i++) {
            // 获取一行所有的数据，即行对象
            XSSFRow row = sheet.getRow(i);
            // 获取单元格对象，行对象的第一个单元格，即此行中第一列对应的单元格
            XSSFCell cell1 = row.getCell(0);
            XSSFCell cell2 = row.getCell(1);
            XSSFCell cell3 = row.getCell(2);
            // 获取单元格中的字符串数据
            String name = cell1.getStringCellValue();
            double age = cell2.getNumericCellValue();
            String city = cell3.getStringCellValue();
            System.out.println(name + ", " + age + ", " + city);
        }

        // 关闭资源
        fileInputStream.close();
        excel.close();
    }
}
