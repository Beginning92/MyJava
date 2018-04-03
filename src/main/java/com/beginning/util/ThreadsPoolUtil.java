package com.beginning.util;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 */
@Slf4j
public class ThreadsPoolUtil {
    private static Map<String, ThreadPoolExecutor> pools = null;

    static {
        init();
    }

    private static void init() {
        if(pools == null){
            pools = new HashMap<String, ThreadPoolExecutor>();
        }
    }

    public ThreadsPoolUtil() {

    }

    /**
     * 线程执行，根据入参poolName确定执行的线程池
     * @param thread 要执行的线程
     * @param poolName 线程池名称
     */
    public static void execute(Thread thread, String poolName) {
        ThreadPoolExecutor pool = pools.get(poolName);
        if (pool == null){
            pool = new ThreadPoolExecutor(5, 10, 2 * 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
            pools.put(poolName, pool);
        }

        try {
            while (true) {
                if (pool.getQueue().size() > 2) {
                    Thread.sleep(500);// 如果线程池队列还有未取出任务，当前线程阻塞，等待线程池队列降低后再执行
                } else {
                    break;
                }
            }

            try {
                pool.allowCoreThreadTimeOut(true);
                pool.execute(thread);
            } catch (RejectedExecutionException e) {
                log.error("线程池出错",e);
//                killThread(unitId);

            } catch (Exception e) {
                log.error("线程池出错",e);
//                killThread(unitId);
//                pool.purge();
//                LogUtil.error2DB("ThreadsPoolUtil",
//                        Thread.currentThread().getName(), unitId, "创建新线程池出错,清空线程池" +
//                                e.getMessage(), e);
            }
        } catch (Exception e) {
            log.error("线程池出错",e);
//            LogUtil.debug("ThreadsPoolUtil", Thread.currentThread().getName() + "线程池出错" + e.getMessage(), "sys");
//            LogUtil.error2DB("ThreadsPoolUtil", null, null,
//                    Thread.currentThread().getName() + "线程池出错" + e.getMessage(), e);
        }
    }

    @SuppressWarnings("unused")
    private static void killThread(String unitId) {
//        try {
//            Map<Thread, StackTraceElement[]> allThread = Thread.getAllStackTraces();
//            int refreshSchThreads = 0;
//            int unitRefreshSchThreads = 0;
//            for (Thread t : allThread.keySet()) {
//                if (t.getName().startsWith("RefreshSchThread")) {
//                    refreshSchThreads++;
//                    if (t.getName().startsWith("RefreshSchThread" + unitId)) {
//                        unitRefreshSchThreads++;
//                        RefreshSchThread thread = (RefreshSchThread) t;
//                        // 线程运行时间超过120秒，直接终止
//                        if (System.currentTimeMillis() - thread.gettStartDate().getTime() > 120000) {
//                            // LogUtil.debug2File("ThreadsPoolUtil","杀掉超时线程",""+thread.getName());
//                            thread.interrupt();
//                        }
//
//                    }
//                }
//            }
//            // LogUtil.debug2DB("ThreadsPoolUtil", "RefreshSch",
//            // "当前线程数:"+allThreads+"刷新号源线程数:"+refreshSchThreads+"当前医院线程数:"+unitRefreshSchThreads+"当前医院:"+unitId,
//            // "SYSTEM");
//        } catch (Exception e) {
//            LogUtil.error("killThread失败", e);
//        }
    }

}
