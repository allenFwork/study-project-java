package com.study.mapper.lec2.interfaces;

import com.study.dao.BaseDao;
import com.study.mapper.lec2.entity.LenovoSkujcsjSpkcJava;

public interface LenovoSkujcsjSpkcJavaMapper extends BaseDao {

    int deleteByPrimaryKey(Integer id);

    int insert(LenovoSkujcsjSpkcJava record);

    int insertSelective(LenovoSkujcsjSpkcJava record);

    LenovoSkujcsjSpkcJava selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LenovoSkujcsjSpkcJava record);

    int updateByPrimaryKey(LenovoSkujcsjSpkcJava record);

    int dropTable();

    int countAll();

}