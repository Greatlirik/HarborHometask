package com.epam.training.zhuk.test.homeTask3;

import com.epam.training.zhuk.homeTask3.harbor.Pier;
import com.epam.training.zhuk.homeTask3.harbor.Ships;
import org.testng.annotations.Test;
;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class CodeTest {
    public BlockingQueue<Ships> shipsQueue = new LinkedBlockingQueue<Ships>();
    public List<Pier> piers = new ArrayList<Pier>();

    @Test
    public void threadWork() {
        CodeTest harbor = new CodeTest();
        ReentrantLock locker = new ReentrantLock();

        for (int i = 1; i < 3; i++) {

            Pier ps = new Pier(harbor.shipsQueue, i, locker);
            harbor.piers.add(ps);

        }

        harbor.piers.forEach(Thread::start);
        for (int i = 1; i < 12; i++) {
            new Ships(0, harbor.piers, harbor.shipsQueue, i, locker).start();
        }


    }
}

