package com.example.myapplication1;

public class ViewModel {
    private Model mdl;

    public ViewModel(Model m){
        this.mdl = m;
    }

    public void setAileronForViewModel(double val) throws InterruptedException {

        this.mdl.setAileron(val);
    }

    public void setElevatorForViewModel(double val) throws InterruptedException {

        this.mdl.setElevator(val);
    }

    public void setThrottleForViewModel(double val) throws InterruptedException {
        this.mdl.setThrottle(val);
    }
    public void setRudderForViewModel(double val) throws InterruptedException {
        this.mdl.setRudder(val);
    }

    public void connectToSimulator(String ip, int port) throws InterruptedException {
        this.mdl.connectToSimulatorInModel(ip, port);
    }
}
