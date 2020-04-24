package com.epam.training.zhuk.homeTask3.harbor;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class Ships extends Thread {
    private static final Logger log = Logger.getLogger(Ships.class.getName());
    private boolean cargoOff = false;
    private int shipName;
    ReentrantLock locker;
    private List<Pier> piers;
    private BlockingQueue<Ships> shipsQueue;
    private int timeToUpload;

    public Ships(int timeToUpload, List<Pier> piers, BlockingQueue<Ships> shipsQueue, int shipName, ReentrantLock locker) {
        super("Корабль " + shipName);
        this.timeToUpload = timeToUpload;
        this.shipName = shipName;
        this.shipsQueue = shipsQueue;
        this.piers = piers;
        this.locker = locker;

    }

    @Override
    public void run() {

        boolean isApplied = false;

        for (Pier piers : piers) {
            if (piers.isFree()) {
                if (piers.isFree()) {
                    piers.setCurrentShip(this);
                    System.out.println("Корабль " + shipName + " нашел свободный причал " + piers.getPierNumber() + " и пришвартовался");
                    locker.lock();
                    isApplied = true;
                    locker.unlock();

                }
                break;
            } else {
                continue;
            }
        }

        if (!isApplied) {
            shipsQueue.offer(this);
            try {
                Thread.sleep(3000 + new Random().nextInt(3000));
                if (!cargoOff) {
                    shipsQueue.remove(this);
                    System.out.println("Корабль " + shipName + " остался с грузом и отплыл на стоянку");
                }
            } catch (InterruptedException e) {
                log.warning("ошибка при ожидании корабля в очереди");
                Thread.currentThread()
                        .interrupt();
                e.printStackTrace();

            }


        }

        if (cargoOff) {
            System.out.println("Корабль " + shipName + " уплыл восвояси");
        }

    }

    public void upload() {
        try {
            Thread.sleep(timeToUpload);
            cargoOff = true;

        } catch (InterruptedException e) {
            log.warning("ошибка при выполнении функции корабля \"загрузка\"");
            Thread.currentThread()
                    .interrupt();
            e.printStackTrace();
        }


    }

    public int getShipName() {
        return shipName;
    }
}
