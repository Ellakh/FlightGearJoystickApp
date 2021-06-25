package com.example.myapplication1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Model {
    private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();
    private PrintWriter printIt;
    private boolean terminate = false;

    public Model() {
        new Thread(new Runnable() {
            public void run() {
                while (!terminate) {
                    try {
/*                        if(taskQueue.isEmpty()){
                            closeTheThread();
                        }*/
                        taskQueue.take().run();
                    } catch (InterruptedException e) {
                        //terminate = true;
                    }
                }
            }
        }).start();
    }

    public void closeTheThread() throws InterruptedException {
        taskQueue.put(new Runnable() {
            public void run() {
                terminate = true;
            }
        });
    }

    void setAileron(double val) throws InterruptedException {
        if(printIt!=null){
            taskQueue.put(new Runnable() {
                public void run() {
                    printIt.print("set /controls/flight/aileron "+val+"\r\n");
                    printIt.flush();
                }
            });
        }

    }

    void setThrottle(double val) throws InterruptedException {
        if(printIt!=null){
            taskQueue.put(new Runnable() {
                public void run() {
                    printIt.print("set /controls/engines/current-engine/throttle "+val+"\r\n");
                    printIt.flush();
                }
            });
        }

    }

    void setRudder(double val) throws InterruptedException {
        if(printIt!=null){
            taskQueue.put(new Runnable() {
                public void run() {
                    printIt.print("set /controls/flight/rudder "+val+"\r\n");
                    printIt.flush();
                }
            });
        }

    }

    void setElevator(double val) throws InterruptedException {
        if(printIt!=null){
            taskQueue.put(new Runnable() {
                public void run() {
                    printIt.print("set /controls/flight/elevator "+val+"\r\n");
                    printIt.flush();
                }
            });
        }
    }
    void connectToSimulatorInModel(String ip, int port) throws InterruptedException {
        taskQueue.put(new Runnable() {
            public void run() {
                Socket fg = null;
                try {
                    fg = new Socket(ip, port);
                    printIt = new PrintWriter(fg.getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
