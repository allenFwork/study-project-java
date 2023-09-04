package com.study.util.excel.big_data;

import org.junit.Test;

public class CsvUtilTest {

    @Test
    public void duplicateRemovalTest() {
        CsvUtil.duplicateRemoval("D:\\documents\\work\\ADF\\202203\\results/BOM_RESULT_CTO.csv", new int[]{0, 1, 2});
    }

}
