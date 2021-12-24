package com.study.util.excel;

import com.study.util.excel.poi_util.PoiExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PoiExcelUtilTest {

    @Test
    public void readExcel() {
        String fileName = "C:\\documents\\work\\temp\\來酷门店信息_整理2.xlsx";
        List list = PoiExcelUtil.readExcel(fileName);
        System.out.println(list);
    }

    @Test
    public void changeExcelContent() {
        String fileName = "C:\\documents\\work\\temp\\來酷门店信息_整理2.xlsx";
        Workbook workbook = PoiExcelUtil.readExcelToWorkbook(fileName);
        String fileName2 = "C:\\documents\\work\\temp\\來酷门店信息_整理3.xlsx";
        File file = new File(fileName2);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (workbook != null)
                    workbook.close();
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
