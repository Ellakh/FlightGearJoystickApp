package com.example.myapplication1;

// importing the required libraries
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import com.example.myapplication1.databinding.ActivityMainBinding;

// MainActivity class - the logic of the user interface
public class MainActivity extends AppCompatActivity {
    // declaring a field to hold the ViewModel
    private ViewModel vm;

    // onCreate - creates the user interface and the logic behind it
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // creating the ViewModel, and setting data binding to it
        this.vm = new ViewModel(new Model());
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // setting a function to be called when the joystick moves
        Joystick js = findViewById(R.id.joystick);
        js.onChange = (aileron, elevator) -> {
            try {
                vm.setAileron(aileron);
                vm.setElevator(elevator);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        };
        binding.setViewModel(vm);
        // setting listeners to the ip and port EditTexts
        EditText ipAddress = findViewById(R.id.ip);
        EditText portNumber = findViewById(R.id.port);
        ipAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                vm.setIp(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        portNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    vm.setPort(s.toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        // setting listeners to the rudder and throttle SeekBars
        SeekBar rudderBar = findViewById(R.id.rudder);
        SeekBar throttleBar = findViewById(R.id.throttle);
        rudderBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vm.setRudder(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        throttleBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vm.setThrottle(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}