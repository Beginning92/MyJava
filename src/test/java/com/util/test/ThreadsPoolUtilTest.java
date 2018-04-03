package com.util.test;

import com.beginning.util.ThreadsPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by beginning on 2016/12/29.
 */
@Slf4j
public class ThreadsPoolUtilTest {

    @Test
    public void executeTest(){
        System.out.println("size:" + Thread.getAllStackTraces().values().size());
        int i = 0;
        while (++i < 50){
            ThreadsPoolUtil.execute(new Thread(new Runnable() {
                public void run() {
                    try {
                        System.out.println(System.currentTimeMillis() + "------" + Thread.getAllStackTraces().values().size());
                        Thread.sleep(2000);
                    }catch (Exception e){
                        log.error("线程池执行报错", e);
                    }
                }
            }), "test");
        }
    }

}
