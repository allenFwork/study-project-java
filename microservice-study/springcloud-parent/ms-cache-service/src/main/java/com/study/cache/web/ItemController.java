package com.study.cache.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.benmanes.caffeine.cache.Cache;
import com.study.cache.pojo.Item;
import com.study.cache.pojo.ItemStock;
import com.study.cache.pojo.PageDTO;
import com.study.cache.service.IItemService;
import com.study.cache.service.IItemStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private IItemService itemService;
    @Autowired
    private IItemStockService stockService;

    @Autowired
    private Cache<Long, Item> itemCache; // JVM中item对象的缓存
    @Autowired
    private Cache<Long, ItemStock> stockCache;// JVM中stock对象的缓存

    @GetMapping("list")
    public PageDTO queryItemPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size) {
        // 分页查询商品
        Page<Item> result = itemService.query()
                .ne("status", 3)
                .page(new Page<>(page, size));

        // 查询库存
        List<Item> list = result.getRecords().stream().peek(item -> {
            ItemStock stock = stockService.getById(item.getId());
            item.setStock(stock.getStock());
            item.setSold(stock.getSold());
        }).collect(Collectors.toList());

        // 封装返回
        return new PageDTO(result.getTotal(), list);
    }

    @PostMapping
    public void saveItem(@RequestBody Item item) {
        itemService.saveItem(item);
    }

    @PutMapping
    public void updateItem(@RequestBody Item item) {
        itemService.updateById(item);
    }

    @PutMapping("stock")
    public void updateStock(@RequestBody ItemStock itemStock) {
        stockService.updateById(itemStock);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        itemService.update().set("status", 3).eq("id", id).update();
    }

    @GetMapping("/{id}")
    public Item findById(@PathVariable("id") Long id) {
        /*
         * 先查询itemCache中是否有数据，
         * 1.如果有数据直接返回
         * 2.如果没有数据，就执行lambda表达式逻辑，将执行结果放入到itemCache中，在返回该结果
         */
        return itemCache.get(id, key -> itemService.query()
                .ne("status", 3)
                .eq("id", id)
                .one()
        );
    }

    @GetMapping("/stock/{id}")
    public ItemStock findStockById(@PathVariable("id") Long id) {
        return stockCache.get(id, key -> stockService.getById(id));
    }
}
