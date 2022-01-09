package cn.com.search.web;

import cn.com.search.core.Result;
import cn.com.search.core.ResultGenerator;
import cn.com.search.service.ElasticSearchService;
import cn.com.search.service.ReadBooksService;
import cn.com.search.vo.BookSearchParam;
import cn.com.search.vo.BookSearchResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/es")
public class ElasticSearchController {

    @Autowired
    ReadBooksService booksService;
    @Autowired
    ElasticSearchService esSearchService;

    @GetMapping("/creatIndex")
    public Result creatIndex() {
        // 做一次全量数据插入, 做一次就行了,将所有的数据写入到elasticsearch中
        booksService.creatIndex();
        return ResultGenerator.genSuccessResult();
    }

    @GetMapping("/search")
    public Result search(@RequestParam(required = false) String keyWord) {
        BookSearchParam bookSearchParam = new BookSearchParam();
        bookSearchParam.setDesc("童话故事");
        List<BookSearchResultVo> books = esSearchService.queryDocumentByParam("test-es", "test-type", bookSearchParam);
        return ResultGenerator.genSuccessResult(books);
    }

}
