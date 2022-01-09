package cn.com.search.service.impl;

import cn.com.search.core.AbstractService;
import cn.com.search.dao.ReadBooksMapper;
import cn.com.search.model.ReadBooks;
import cn.com.search.service.ElasticSearchService;
import cn.com.search.service.ReadBooksService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/12/16.
 */
@Service
@Transactional
@Slf4j
public class ReadBooksServiceImpl extends AbstractService<ReadBooks> implements ReadBooksService {

    @Resource
    private ReadBooksMapper readBooksMapper;
    @Resource
    private ElasticSearchService elasticSearchService;

    public Integer getCount() {
        return readBooksMapper.selectCount(null);
    }

    @Override
    public List<ReadBooks> search(String keyWords) {
        Condition condition = new Condition(ReadBooks.class);
        Criteria criteria = condition.createCriteria();
        criteria.andLike("discription", "%" + keyWords + "%");
        return super.findByCondition(condition);
    }

    /**
     * 创建索引：
     * 将表中的数据导入到elasticsearch中
     */
    @Override
    public Boolean creatIndex() {
    	// 设置导入数据的条数
        int totalCount = 5640;
        // 设置分页处理
        final int pageSize = 100;
        int pageIndex = totalCount / pageSize;
        if (totalCount % pageSize != 0)
            pageIndex++;
        // 开多线程做，在系统空闲的时候做，基本上一个月做一次。
        for (int i = 1; i <= pageIndex; i++) {
            try {
                log.info("正在创建第{}页索引数据", i);

				/**
				 * com.github.pagehelper.PageHelper 分页插件处理
				 * 在线程中注入一个limit, 执行 super.findAll() 时, 查询该页的数据
				 */
				PageHelper.startPage(i, pageSize);
                List<ReadBooks> books = super.findAll();

                List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
                for (ReadBooks book : books) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", book.getId());
                    map.put("praise_rate", book.getPraiseRate());
                    map.put("discription", book.getDiscription());
                    map.put("create_time", book.getCreateTime());
                    map.put("level", book.getLevel());
                    map.put("isbn", book.getIsbn());
                    map.put("language", book.getLanguage());
                    map.put("type", book.getType());
                    map.put("reader_number", book.getReaderNumber());
                    map.put("imgurl", book.getImgurl());
                    map.put("price", book.getPrice());
                    map.put("name", book.getName());
                    data.add(map);
                }
                // elasticsearch操作服务类，向 test-es索引中的 my_es_type 中写数据
                elasticSearchService.searchBulkIn(data, "test-es", "test-type");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

}
