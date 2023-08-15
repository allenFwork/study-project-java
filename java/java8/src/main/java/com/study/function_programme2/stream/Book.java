package com.study.function_programme2.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode // 用于数据去重处理使用
public class Book {
    // id
    private Long id;
    // 书名
    private String name;
    // 分类，一个实例："哲学，小说"，既属于哲学，也属于小说
    private String category;
    // 评分
    private Integer score;
    // 简介
    private String intro;
}
