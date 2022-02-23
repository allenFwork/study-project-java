package com.study.data_process;

import com.study.dao.BaseDao;
import com.study.mapper.lec.entity.LenovoSkujcsjSpkcBackupDto;
import com.study.mapper.lec2.entity.LenovoSkujcsjSpkcJava;
import com.study.mapper.lec2.interfaces.LenovoSkujcsjSpkcJavaMapper;
import com.study.util.data.AbsDefaultDataSourceProcess;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 来酷数据库处理类
 */
public class LecDataProcess extends AbsDefaultDataSourceProcess<LenovoSkujcsjSpkcBackupDto, LenovoSkujcsjSpkcJava> {

    @Autowired
    private LenovoSkujcsjSpkcJavaMapper lenovoSkujcsjSpkcJavaMapper;

    @Override
    protected Class getTargetClass() {
        return LecDataProcess.class;
//        return this.getClass();
    }

    @Override
    protected String getSql() {
        String sql = "select * from abc";
        return sql;
    }

    @Override
    protected BaseDao getDao() {
        return lenovoSkujcsjSpkcJavaMapper;
    }

    @Override
    protected List<LenovoSkujcsjSpkcJava> convertDTO(LenovoSkujcsjSpkcBackupDto lenovoSkujcsjSpkcBackupDto) {
        return null;
    }
}
