package com.study.jvm.actual_combat.practice_demo.oom.service;


import com.study.jvm.actual_combat.practice_demo.oom.pojo.ArticleDto;

public interface ArticleService {
    void saveArticle(ArticleDto article);
    void asyncSaveArticle(ArticleDto article);
}
