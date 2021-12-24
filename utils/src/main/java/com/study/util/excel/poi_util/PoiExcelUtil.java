package com.study.util.excel.poi_util;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoiExcelUtil {

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    private static Logger logger = LoggerFactory.getLogger(PoiExcelUtil.class);

    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     *
     * @param inputStream 读取文件的输入流
     * @param fileType    文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 解析Excel数据
     *
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static List<Map<String, Object>> parseExcel(Workbook workbook) {
        List<Map<String, Object>> resultDataList = new ArrayList<>();
        // 解析sheet
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            // 获取每个Sheet表对象
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }

            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                System.out.println("解析Excel失败，在第一行没有读取到任何数据！");
            }

            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum + 1;
            // 获取每个Sheet内的行数
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                // 获取除了第一行的每一行
                Row row = sheet.getRow(rowNum);

                if (null == row) {
                    continue;
                }

                Map<String, Object> resultData = convertRowToData(row);
                if (null == resultData) {
                    System.out.println("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                    continue;
                }
                resultDataList.add(resultData);
            }
        }
        return resultDataList;
    }

    /**
     * 提取每一行中需要的数据，构造成为一个结果数据对象
     * 当该行中有单元格的数据为空或不合法时，忽略该行的数据
     *
     * @param row 行数据
     * @return 解析后的行数据对象，行数据错误时返回null
     */
    private static Map<String, Object> convertRowToData(Row row) {
        Map<String, Object> resultData = new HashMap<>();

        Cell cell;
        int cellNum = 0;

        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            // 获取单元格
            cell = row.getCell(i);
            String value = convertCellValueToString(cell);
            resultData.put("rowIndex:[" + row.getRowNum() + "], colIndex:[" + i + "]", value);
        }

        return resultData;
    }

    /**
     * 将单元格内容转换为字符串
     *
     * @param cell
     * @return
     */
    private static String convertCellValueToString(Cell cell) {
        if (cell == null) {
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();

                // 格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0");
                returnValue = df.format(doubleValue);
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                returnValue = cell.getCellFormula();
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }

    /**
     * 读取Excel文件内容
     *
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static List<?> readExcel(String fileName) {

        Workbook workbook = null;
        FileInputStream inputStream = null;

        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                System.out.println("指定的Excel文件不存在！");
                return null;
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            List<Map<String, Object>> resultDataList = parseExcel(workbook);

            return resultDataList;
        } catch (Exception e) {
            logger.info("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.info("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }

    /**
     * 读取excel文件，并修改其中内容
     *
     * @param fileName
     * @return
     */
    public static Workbook readExcelToWorkbook(String fileName) {

        Workbook workbook = null;
        FileInputStream inputStream = null;

        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                System.out.println("指定的Excel文件不存在！");
                return null;
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);


            // 解析sheet
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
                // 获取每个Sheet表对象
                Sheet sheet = workbook.getSheetAt(sheetNum);

                // 校验sheet是否合法
                if (sheet == null) {
                    continue;
                }

                // 获取第一行数据
                int firstRowNum = sheet.getFirstRowNum();
                Row firstRow = sheet.getRow(firstRowNum);
                if (null == firstRow) {
                    System.out.println("解析Excel失败，在第一行没有读取到任何数据！");
                }

                // 解析每一行的数据，构造数据对象
                int rowStart = firstRowNum + 1;
                // 获取每个Sheet内的行数
                int rowEnd = sheet.getPhysicalNumberOfRows();
                for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                    // 获取除了第一行的每一行
                    Row row = sheet.getRow(rowNum);

                    if (null == row) {
                        continue;
                    }

                    for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
                        addComment(row.getCell(i), "批注信息" + i, fileType);
                    }
                }
            }
            return workbook;

        } catch (Exception e) {
            logger.info("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            return null;
        } finally {
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.info("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }

    }

    /**
     * 给Cell添加批注
     *
     * @param cell      单元格
     * @param value     批注内容
     * @param extension 扩展名
     */
    public static void addComment(Cell cell, String value, String extension) {
        Sheet sheet = cell.getSheet();
        cell.removeCellComment();
        if ("xls".equals(extension)) {
            ClientAnchor anchor = new HSSFClientAnchor();
            // 关键修改
            anchor.setDx1(0);
            anchor.setDx2(0);
            anchor.setDy1(0);
            anchor.setDy2(0);
            anchor.setCol1(cell.getColumnIndex());
            anchor.setRow1(cell.getRowIndex());
            anchor.setCol2(cell.getColumnIndex() + 5);
            anchor.setRow2(cell.getRowIndex() + 6);
            // 结束
            Drawing drawing = sheet.createDrawingPatriarch();
            Comment comment = drawing.createCellComment(anchor);
            // 输入批注信息
            comment.setString(new HSSFRichTextString(value));
            // 将批注添加到单元格对象中
            cell.setCellComment(comment);
        } else if ("xlsx".equals(extension)) {
            ClientAnchor anchor = new XSSFClientAnchor();
            // 关键修改
            anchor.setDx1(0);
            anchor.setDx2(0);
            anchor.setDy1(0);
            anchor.setDy2(0);
            anchor.setCol1(cell.getColumnIndex());
            anchor.setRow1(cell.getRowIndex());
            anchor.setCol2(cell.getColumnIndex() + 5);
            anchor.setRow2(cell.getRowIndex() + 6);
            // 结束
            Drawing drawing = sheet.createDrawingPatriarch();
            Comment comment = drawing.createCellComment(anchor);
            // 输入批注信息
            comment.setString(new XSSFRichTextString(value));
            // 将批注添加到单元格对象中
            cell.setCellComment(comment);
        }
    }
}
