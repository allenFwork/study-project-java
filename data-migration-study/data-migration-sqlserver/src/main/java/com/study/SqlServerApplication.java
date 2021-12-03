//package com.study;
//
//import java.util.List;
//import java.util.Vector;
//import java.util.concurrent.*;
//
//public class SqlServerApplication {
//
//    public static void main(String[] args) {
//
//        // 数据库中总记录数
//        long rows = 2177847407L;
//
//        // 核心线程数
//        int kThreads = Runtime.getRuntime().availableProcessors() * 2;
//        // 任务数(这里需要求一下平均每个任务需要执行的任务id大小是多少，实际测试中，20-30w 快则 2秒，慢则3-5秒，这个阈值是比较理想的
//        // 也就是 总记录数/任意数 等到想要的平均任务数)
//        Long talks = (rows / 8000) + 1;
//
//        // 线程池
//        ExecutorService threadPool = Executors.newFixedThreadPool(kThreads);
//
//        // 数据起始位
//        Long startIndex = 0L;
//
//        // 数据结束位
//        Long endIndex = startIndex + talks;
//
//        // 存放线程执行结果
//        List<Future> list = new Vector<>();
//        // 执行多少次任务 = 结束位置不小于总记录数
//        for (Long i = 0l; i < rows; i = endIndex) {
//            final Future submit = threadPool.submit(new DataThread(startIndex, endIndex, new FaultRealMapper()));
//            //每次执行完成，开始id+1
//            startIndex = endIndex + 1;
//            //结束id=开始id+任务数
//            endIndex = startIndex + talks;
//            list.add(submit);
//        }
//        // 打印结果
//        list.forEach(dx -> {
//            try {
//                System.out.println(dx.get());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//
//    // 线程操作资源类
//    class DataThread implements Callable {
//
//        private FaultRealMapper faultRealMapper;
//        private Long startId, endId;
//
//        public DataThread(Long startId, Long endId, FaultRealMapper faultRealMapper) {
//            this.faultRealMapper = faultRealMapper;
//            this.startId = startId;
//            this.endId = endId;
//            System.out.println("startId:" + startId + ",endId:" + endId);
//        }
//
//        @Override
//        public Object call() throws Exception {
//            System.out.println("开始执行");
//            faultRealMapper.insert(startId, endId);
//            return "执行成功";
//        }
//    }
//
//}
