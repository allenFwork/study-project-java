package com.study.util.data;

import com.study.dao.BaseDao;
import com.study.util.date.LocalDateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


@Slf4j
public abstract class AbstractDataProcess<T, V> {

    /**
     * 拉取大小,注意： MySql 只能为此值才生效为流的读取方式
     * SqlSever需要单独设置
     */
    protected int fetchSizeMysql = Integer.MIN_VALUE;

    /**
     * 保存大小,不能太大不然会因为传输超长报错
     */
    protected int insertBatchSize = 100;

    /**
     * 线程池阻塞队列大小
     */
    protected int blockQueueSize = Integer.MAX_VALUE;

    protected String jobName = getTargetClass().getSimpleName();

    protected PreparedStatement ps = null;

    public Integer getBatchSize() {
        return insertBatchSize;
    }

    public Integer getFetchSize() {
        return fetchSizeMysql;
    }

    /**
     * 内部类，Callable接口类型，用于执行插入操作的任务
     * @param <V>
     */
    public class TaskCallable<V> implements Callable {

        private List<V> entities;
        private BaseDao baseDao;

        public TaskCallable(List<V> entities, BaseDao baseDao) {
            this.entities = entities;
            this.baseDao = baseDao;
        }

        @Override
        public Integer call() {
            return baseDao.insertBatch(entities);
        }

    }

    /*============================================= 抽象方法,子类实现 =============================================*/
    /**
     * 获取目标类
     * @return
     */
    abstract protected String getDriver();

    /**
     * 获取目标类
     * @return
     */
    abstract protected Class getTargetClass();

    /**
     * 要执行的sql
     * @return
     */
    abstract protected String getSql();

    /**
     * 获取连接
     * @return
     */
    abstract protected Connection getConnection() throws SQLException;

    /**
     * 获取目标类
     *
     * @return
     */
    abstract protected BaseDao getDao();

    /**
     * bean转换
     */
    protected abstract List<V> convertDTO(T t);
    /*============================================= 抽象方法,子类实现 =============================================*/

    /**
     * bean转换
     */
    protected T convertData(ResultSet rs, Class outputClass) {
        ResultSetMapper<T> resultSetMapper = new ResultSetMapper();
        Object obj = resultSetMapper.mapResultSetToObject(rs, outputClass);
        return (T) obj;
    }

    /**
     * 默认的获取结果集方法
     * @param rs
     * @return
     */
    protected List<V> getByResultSet(ResultSet rs) throws SQLException {
        T entity = convertData(rs, getTargetClass());
        return convertDTO(entity);
    }

    /**
     * 获取线程池
     *
     * @return
     */
    protected ExecutorService getExecutorService() {
        return new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), 100,
                20L, TimeUnit.SECONDS,
                new LinkedBlockingQueue(blockQueueSize));
    }

    /**
     * 预留方法
     */
    protected void beforeProcess() {

    }

    /**
     * 执行任务处理
     * @return
     */
    public TaskResultModel doProcess() {
        LocalDateTime startTime = LocalDateTime.now();
        long currentTimeMillis = System.currentTimeMillis();
        log.info("{} start time = {}", jobName, currentTimeMillis);
        int count = 0;
        ResultSet rs = null;
        Connection connDB = null;
        long endTime = 0;
        try {
            beforeProcess();
            log.info("{} start 001", jobName);
            Class.forName(getDriver());
            log.info("{} start 002", jobName);
            connDB = getConnection();
            log.info("{} start 003", jobName);
            String sql = getSql();
            log.info("{} start 004", jobName);
            ps = connDB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            // 为了设置fetchSize,必须设置为false
            connDB.setAutoCommit(false);
            log.info("{} start 005", jobName);
            Integer fetchSize = getFetchSize();
            ps.setFetchSize(fetchSize);
//            ps.setFetchDirection(ResultSet.FETCH_REVERSE);
            log.info("{} start  006", jobName);
            rs = ps.executeQuery();
            List<V> entities = new ArrayList();
            ExecutorService executorService = getExecutorService();
            List<Future<Integer>> futures = new ArrayList<>();
            log.info("{} start  007", jobName);
            while (rs.next()) {
                List<V> convertDOs = getByResultSet(rs);
                if (CollectionUtils.isEmpty(convertDOs)) {
                    continue;
                }
                entities.addAll(convertDOs);
                Integer insertBatchSize = getBatchSize();
                if (entities.size() >= insertBatchSize) {
                    log.info("{} 批量保存开始，insertBatchSize={}", jobName, entities.size());
                    Future<Integer> future = executorService.submit(new TaskCallable(entities, getDao()));
                    futures.add(future);
                    entities = new ArrayList();
                }
            }
            if (!CollectionUtils.isEmpty(entities)) {
                Future<Integer> future = executorService.submit(new TaskCallable(entities, getDao()));
                futures.add(future);
            }
            log.info("{} start 008", jobName);
            for (Future<Integer> future : futures) {
                count += future.get();
            }
            endTime = System.currentTimeMillis();
            log.info("{} endTime time", jobName, endTime);
            log.info("{} 批量保存结束，count={}, time={}ms", jobName, count, (endTime - currentTimeMillis));
        } catch (Exception e) {
            log.error("{} do Process error : {}", jobName, e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.error("{}do Process colse rs error : {}", jobName, e);
                }
            }
            if (connDB != null) {
                try {
                    connDB.close();
                } catch (SQLException e) {
                    log.error("{}do Process close connDB error: {}", jobName, e);
                }
            }
        }
        TaskResultModel taskResultModel = new TaskResultModel();
        taskResultModel.setTaskNum(count);
        taskResultModel.setTaskName(jobName);
        taskResultModel.setStartTime(LocalDateUtil.formatDate(startTime));
        taskResultModel.setEndTime(LocalDateUtil.formatDate(LocalDateTime.now()));
        taskResultModel.setCostTime("" + endTime + "ms");
        return taskResultModel;
    }

}
