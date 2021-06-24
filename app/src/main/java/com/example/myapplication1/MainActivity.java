package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import com.example.myapplication1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Joystick joystick;
    private ViewModel vm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.vm = new ViewModel(new Model());
        this.binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.binding.setViewModel(vm);
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
                vm.setPort(Integer.parseInt(s.toString()));
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
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
        this.joystick = new Joystick(getApplicationContext());
        //joystick = findViewById(R.id.joystickCircle);
        this.joystick.onChange = (aileron, elevator)-> {
            try {
                this.vm.setAileronForViewModel(aileron);
                this.vm.setElevatorForViewModel(elevator);
            } catch (InterruptedException ie) {
                Log.d("123", "456");
            }
        };
/*
        LayoutInflater layoutInflater = getLayoutInflater();
        View joystickView = layoutInflater.inflate(R.class., null);
        ViewGroup mainView = findViewById(R.id.layout);
        mainView.addView(joystickView, 0);
*/

    }
}