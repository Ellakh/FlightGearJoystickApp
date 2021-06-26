package com.example.myapplication1;

// importing the required libraries
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

// ViewModel class - the bridge between the View and the Model
public class ViewModel extends BaseObservable {
    // declaring a mdl field to hold a reference to the model, and rudder, throttle, ip and port fields to data-bind to
    private Model mdl;
    private int rudder;
    private int throttle;
    private String ip;
    private String port;

    // ViewModel - constructor of the ViewModel class
    public ViewModel(Model m){
        this.mdl = m;
        this.rudder = 100;
    }

    // setIp - sets the ip address using the given value
    public void setIp(String ipAddress) {
        this.ip = ipAddress;
        notifyPropertyChanged(BR.ip);
    }

    // getIp - returns the ip address
    @Bindable
    public String getIp() {
        return this.ip;
    }

    // setPort - sets the port number using the given value
    public void setPort(String portNumber) {
        this.port = portNumber;
        notifyPropertyChanged(BR.port);
    }

    // getPort - returns the port number
    @Bindable
    public String getPort() {
        return this.port;
    }

    // setAileron - sets the aileron using the given value
    public void setAileron(double val) throws InterruptedException {
        this.mdl.setAileron(val);
    }

    // setElevator - sets the elevator using the given value
    public void setElevator(double val) throws InterruptedException {
        this.mdl.setElevator(val);
    }

    // setThrottle - sets the throttle using the given value
    public void setThrottle(int throttle) {
        this.throttle = throttle;
        notifyPropertyChanged(BR.throttle);
        try {
            // converting the value to fit the required range
            this.mdl.setThrottle(throttle / 100.0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // getThrottle - returns the throttle value
    @Bindable
    public int getThrottle() {
        return this.throttle;
    }

    // setRudder - sets the rudder using the given value
    public void setRudder(int rudder) {
        this.rudder = rudder;
        notifyPropertyChanged(BR.rudder);
        try {
            // converting the value to fit the required range
            this.mdl.setRudder((rudder - 100) / 100.0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // getRudder - returns the rudder value
    @Bindable
    public int getRudder() {
        return this.rudder;
    }

    // connectToSimulator - connects the client to the server (the simulator) over TCP/IP protocol
    public void connectToSimulator() {
        // checking if the user entered a valid ip address and port number
        String[] ipCheck = this.ip.split("\\.");
        if (ipCheck.length != 4) {
            this.ip = "Please enter a valid IP";
            notifyPropertyChanged(BR.ip);
        } else {
            for (String ipPart : ipCheck) {
                try {
                    int part = Integer.parseInt(ipPart);
                    if (part < 0 || part > 255) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException nfe) {
                    this.ip = "Please enter a valid IP";
                    notifyPropertyChanged(BR.ip);
                    return;
                }
            }
        }
        try {
            int portNum = Integer.parseInt(this.port);
            if (portNum < 0 || portNum > 65535) {
                throw new NumberFormatException();
            }
            // after input validation - connecting to the simulator
            this.mdl.connectToSimulatorInModel(this.ip, Integer.parseInt(this.port));
        } catch (NumberFormatException nfe) {
            this.port = "Please enter a valid Port";
            notifyPropertyChanged(BR.port);
        } catch (InterruptedException ie) {
            this.ip = "Connection To simulator failed";
            notifyPropertyChanged(BR.ip);
            this.port = "Please verify your ip and port";
            notifyPropertyChanged(BR.port);
        }
    }
}
