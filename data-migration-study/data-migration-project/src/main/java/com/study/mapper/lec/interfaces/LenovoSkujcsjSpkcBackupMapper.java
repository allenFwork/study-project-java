package com.study.mapper.lec.interfaces;

import com.study.mapper.lec.entity.LenovoSkujcsjSpkcBackup;
import org.springframework.stereotype.Component;

//@Component
public interface LenovoSkujcsjSpkcBackupMapper {

    int insert(LenovoSkujcsjSpkcBackup record);

    int insertSelective(LenovoSkujcsjSpkcBackup record);

    int deleteTable();

    int countAll();
}