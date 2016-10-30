package com.example.chitphentom.nfcprototype;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// buttons
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// RNG Text
import com.example.chitphentom.nfcprototype.RNGText;

public class MainActivity extends AppCompatActivity {

    Button Refresh;
    TextView RNGField, IPAddr, SSID, P_IP, P_SSID, P_RNGText;
    String rngStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        Refresh = (Button) findViewById(R.id.refresh);
        // register Refresh routine
        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

        // register text fields
        RNGField = (TextView) findViewById(R.id.RNGText);
        IPAddr = (TextView) findViewById(R.id.Wifi);
        SSID = (TextView) findViewById(R.id.Domain);

        P_IP = (TextView) findViewById(R.id.ReceivedIP);
        P_SSID = (TextView) findViewById(R.id.ReceivedDomain);
        P_RNGText = (TextView) findViewById(R.id.ReceivedRNG);

        refresh();
    }

    private void refresh() {
        // set rng text
        rngStr = RNGText.rng_alphanum(5);
        RNGField.setText(rngStr);

        // make sure NFC is on
        

        // TODO: Get Network Infomation

    }
}
