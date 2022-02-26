package study.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * java的过滤器原生写法：实现 Filter
 * zuul的四种过滤器：第一种
 */
@Component // 将过滤器注入到spring容器中
public class CustomerPreFilter extends ZuulFilter {

    /**
     * 用来定义此过滤器的类型
     * @return
     */
    @Override
    public String filterType() {
        // 第一种类型的 前置 pre 类型过滤器
        return FilterConstants.PRE_TYPE;

        // 第二种类型的 routing 类型过滤器
//        return FilterConstants.ROUTE_TYPE;

        // 第三种类型的 post 类型过滤器
//        return FilterConstants.POST_TYPE;

        // 第四种类型的 error 类型过滤器
//        return FilterConstants.ERROR_TYPE;
    }

    /**
     * 和 java 自带的过滤器优先级一样
     * 优先级，数字越小，优先级越大
     * 当有两个 PRE 类型，用这个来确定同类型过滤器的级别
     */
    @Override
    public int filterOrder() {
        // 0 级别最大
//        return 0;
        // FilterConstants.PRE_DECORATION_FILTER_ORDER zuul的前置过滤器默认级别
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 1;
    }

    /**
     * 此路由器是否启用
     * 返回 true  表示启用
     * 返回 false 表示不用
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 此过滤器的过滤逻辑
     */
    @Override
    public Object run() throws ZuulException {

        // RequestContext 就是一个 ConcurrentHashMap，这个 map 中有许多数据
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        System.out.println(request.getRemoteAddr() + "访问了" + request.getRequestURI()); // 0:0:0:0:0:0:0:1访问了/api/power/getPower.do

        String url = requestContext.get(FilterConstants.REQUEST_URI_KEY).toString();
        System.out.println("路由后的地址：" + url); // 路由后的地址：/getPower.do

        return null;
    }
}
