package com.study.cache.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.cache.pojo.Item;

public interface IItemService extends IService<Item> {
    void saveItem(Item item);
}
