package com.example.myapplication1;

// importing the required libraries
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

// Model class - the main business logic behind the project
public class Model {
    // declaring a taskQueue to hold the tasks, printIt to interact with the simulator, and terminate flag
    private BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();
    private PrintWriter printIt;
    private boolean terminate = false;

    // Model - constructor of the Model class
    public Model() {
        // starting an infinite loop in a new thread that runs the tasks by order
        new Thread(() -> {
            while (!terminate) {
                try {
                    // if there are tasks left to execute, execute the next one
                    if (!taskQueue.isEmpty()) {
                        taskQueue.take().run();
                    }
                } catch (InterruptedException e) {
                    // in case of an error, stops the loop
                    try {
                        closeTheThread();
                    } catch (InterruptedException ie) {
                        terminate = true;
                    }
                }
            }
        }).start();
    }

    // closeTheThread - adds the loop terminator task to the queue
    public void closeTheThread() throws InterruptedException {
        taskQueue.put(new Runnable() {
            public void run() {
                terminate = true;}
        });
    }

    // setAileron - adds the "set aileron" send to simulator command task to the queue
    public void setAileron(double val) throws InterruptedException {
        if(printIt!=null){
            taskQueue.put(new Runnable() {
                public void run() {
                    printIt.print("set /controls/flight/aileron " + val + "\r\n");
                    printIt.flush();
                }
            });
        }
    }

    // setThrottle - adds the "set throttle" send to simulator command task to the queue
    public void setThrottle(double val) throws InterruptedException {
        if(printIt!=null){
            taskQueue.put(new Runnable() {
                public void run() {
                    printIt.print("set /controls/engines/current-engine/throttle " + val + "\r\n");
                    printIt.flush();
                }
            });
        }
    }

    // setRudder - adds the "set rudder" send to simulator command task to the queue
    public void setRudder(double val) throws InterruptedException {
        if(printIt!=null){
            taskQueue.put(new Runnable() {
                public void run() {
                    printIt.print("set /controls/flight/rudder " + val + "\r\n");
                    printIt.flush();
                }
            });
        }
    }

    // setElevator - adds the "set elevator" send to simulator command task to the queue
    public void setElevator(double val) throws InterruptedException {
        if(printIt!=null){
            taskQueue.put(new Runnable() {
                public void run() {
                    printIt.print("set /controls/flight/elevator " + val + "\r\n");
                    printIt.flush();
                }
            });
        }
    }

    // connectToSimulatorInModel - adds the connect to simulator task to the queue
    public void connectToSimulatorInModel(String ip, int port) throws InterruptedException {
        taskQueue.put(new Runnable() {
            public void run() {
                Socket fg = null;
                try {
                    // creating a socket using the given ip address and port number
                    fg = new Socket(ip, port);
                    // creating a PrintWriter object to communicate with the simulator
                    printIt = new PrintWriter(fg.getOutputStream(), true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
