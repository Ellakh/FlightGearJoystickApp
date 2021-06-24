package com.example.myapplication1;

import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingMethod;
import androidx.databinding.InverseBindingMethods;
import androidx.databinding.ObservableField;

import java.util.Locale;
import java.util.regex.*;

@InverseBindingMethods({
        @InverseBindingMethod(type = SeekBar.class, attribute = "android:progress"),
})

public class ViewModel extends BaseObservable {
    public ObservableField<Integer> rudder = new ObservableField<>();
    public ObservableField<Integer> throttle = new ObservableField<>();
    public ObservableField<String> ip = new ObservableField<>();
    public ObservableField<Integer> port = new ObservableField<>();

    private Model mdl;
    private double aileron;
    //private int rudder;
    //private int throttle;
    private double elevator;
    //private String ip;
    //private int port;

    public ViewModel(Model m){
        this.mdl = m;
    }

    public void setIp(String ipAddress) {
        //this.ip = ipAddress;
        this.ip.set(ipAddress);
        notifyPropertyChanged(BR.ip);
    }

    @Bindable
    public ObservableField<String> getIp() {
        return this.ip;
    }

    public void setPort(int portNumber) {
        //this.port = portNumber;
        this.port.set(portNumber);
        notifyPropertyChanged(BR.port);
    }

    @Bindable
    public ObservableField<Integer> getPort() {
        return this.port;
    }

    public void setAileronForViewModel(double val) throws InterruptedException {
        this.mdl.setAileron(val);
        //this.aileron = val;
        //notifyPropertyChanged(BR.);
    }

    public void setElevatorForViewModel(double val) throws InterruptedException {
        this.mdl.setElevator(val);
        //this.elevator = val;
        //notifyPropertyChanged(BR.elevator);
    }

    public void setThrottle(int throttle) {
        //this.throttle = throttle;
        try {
            this.mdl.setThrottle(throttle / 100.0);
        } catch (InterruptedException e) {
            Log.d("222", "333");
        }
        notifyPropertyChanged(BR.throttle);
    }

    @Bindable
    public ObservableField<Integer> getThrottle() {
        return this.throttle;
    }

    public void setRudder(int rudder) {
        //this.rudder = rudder;
        try {
            this.mdl.setRudder((rudder - 100) / 100.0);
        } catch (InterruptedException e) {
            Log.d("444", "555");
        }
        notifyPropertyChanged(BR.rudder);
    }

    @Bindable
    public ObservableField<Integer> getRudder() {
        return this.rudder;
    }

    @BindingAdapter("android:text")
    public static void setText(TextView text, int val) {
        text.setText(String.format(Locale.US ,"%d", val));
    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getText(TextView text) {
        return Integer.parseInt(text.getText().toString());
    }

    @BindingAdapter("android:progress")
    public static void setProgress(SeekBar bar, int progress) {
        bar.setProgress(progress);
    }

    @InverseBindingAdapter(attribute = "android:progress")
    public static int getProgress(SeekBar bar) {
        return bar.getProgress();
    }

    public void connectToSimulator() {
        String regexExp = "[0-9]{1,4}\\.[0-9]{1,4}\\.[0-9]{1,4}"; //parse string
        if (this.port != null && this.ip != null && this.port.get() >=0 && this.port.get() <= 65535 && Pattern.matches(regexExp, this.ip.get())) {
            try {
                this.mdl.connectToSimulatorInModel(this.ip.get(), this.port.get());
            } catch (InterruptedException e) {
                Log.d("777", "888");
            }
        }
    }
}
