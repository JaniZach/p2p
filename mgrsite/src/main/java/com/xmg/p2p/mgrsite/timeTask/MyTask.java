package com.xmg.p2p.mgrsite.timeTask;

import org.springframework.stereotype.Service;

/**
 * Spring中的定时器(Quartz)
 */
@Service("myTask")
public class MyTask {
    public void doTask(){
        System.out.println("=============================");
        System.out.println("定时任务执行... ...");
        System.out.println("=============================");
    }
}
