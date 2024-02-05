package com.study.cache.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.cache.service.IItemStockService;
import com.study.cache.mapper.ItemStockMapper;
import com.study.cache.pojo.ItemStock;
import org.springframework.stereotype.Service;

@Service
public class ItemStockService extends ServiceImpl<ItemStockMapper, ItemStock> implements IItemStockService {
}
