package com.study.util.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.study.util.excel.handeler.CommentWriteHandler;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 测试写批注到excel中
 */
public class CommentWriteTest {

    @Test
    public void writeCommentExcel() throws FileNotFoundException {
        // 生成表格数据
        List<List<Object>> dataList = new ArrayList<>();
        dataList.add(new ArrayList<>(Arrays.asList(new Object[]{"表头11", "表头2", "表头3", "表头4"})));
        dataList.add(new ArrayList<>(Arrays.asList(new Object[]{"表头1", "表头2", "表头3", "表头4"})));
        dataList.add(new ArrayList<>(Arrays.asList(new Object[]{"表头31", "表头2", "表头3", "表头4"})));

        String sheetName = "模板";
        List<Map<String, String>> commentList = new ArrayList<>();
        commentList.add(CommentWriteHandler.createCommentMap(sheetName, 0, 1, "第一条批注。"));
        commentList.add(CommentWriteHandler.createCommentMap(sheetName, 0, 1, "第二条批注。"));

        // 导出文件
        File file = new File("C:\\documents\\work\\temp\\文件名称.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ExcelWriter excelWriter = EasyExcel.write(fileOutputStream).inMemory(Boolean.TRUE).registerWriteHandler(new CommentWriteHandler(commentList, "xlsx")).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        excelWriter.write(dataList, writeSheet);
        //千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

    @Test
    public void writeCommentExcel2() throws FileNotFoundException {
        // 生成表格数据
        List<List<Object>> dataList = new ArrayList<>();
        dataList.add(new ArrayList<>(Arrays.asList(new Object[]{"表头11", "表头2", "表头3", "表头4"})));
        dataList.add(new ArrayList<>(Arrays.asList(new Object[]{"表头1", "表头2", "表头3", "表头4"})));
        dataList.add(new ArrayList<>(Arrays.asList(new Object[]{"表头31", "表头2", "表头3", "表头4"})));

        String sheetName = "模板";
        List<Map<String, String>> commentList = new ArrayList<>();
        commentList.add(CommentWriteHandler.createCommentMap(sheetName, 0, 1, "第一条批注。"));
        commentList.add(CommentWriteHandler.createCommentMap(sheetName, 0, 1, "第二条批注。"));

        // 导出文件
        File file = new File("C:\\documents\\work\\temp\\文件名称.xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        ExcelWriter excelWriter = EasyExcel.write(fileOutputStream).inMemory(Boolean.TRUE).registerWriteHandler(new CommentWriteHandler(commentList, "xlsx")).build();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        excelWriter.write(dataList, writeSheet);
        // 千万别忘记finish 会帮忙关闭流
        excelWriter.finish();
    }

}
