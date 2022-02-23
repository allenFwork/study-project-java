package com.study.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 基类
 **/
@SuppressWarnings("MybatisMapperMethodInspection")
public interface BaseDao<T> {

    int deleteByPrimaryKey(Long id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    /**
     * 批量保存
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<T> list);

    List<BasicDataModel> selectByCondition(BasePNCondition basePNCondition);

}
