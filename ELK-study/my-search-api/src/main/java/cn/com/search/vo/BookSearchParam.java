package cn.com.search.vo;

import lombok.Data;

@Data
public class BookSearchParam {

    private String desc;
    private String bookName;
    private Double price;

    private Integer pageIndex = 1;
    private Integer pageSize = 5;

    // 分组统计字段
    private String statField = "level";

    private String highlightedField = "discription";

    // 也可以设置or
    private String searchType = "and";

    @Override
    public String toString() {
        return "BookSearchParam [desc=" + desc + ", bookName=" + bookName + ", price=" + price + ", pageIndex="
                + pageIndex + ", pageSize=" + pageSize + "]";
    }

}
