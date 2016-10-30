package com.example.chitphentom.nfcprototype;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// buttons
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// toasts
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button Refresh;
    TextView RNGField, IPAddr, SSID, P_IP, P_SSID, P_RNGText;
    String rngStr;

    // network information
    int IP;
    String NETNAME;

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

        // class globals
        IP = 0;
        NETNAME = "";

        refresh();
    }

    private void refresh() {
        // set rng text
        rngStr = RNGText.rng_alphanum(5);
        RNGField.setText(rngStr);

        // Get Network Information
        WifiManager wifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiObj.getConnectionInfo();

        IP = wifiInf.getIpAddress();

        if (IP != 0) {
            IPAddr.setText(String.format(Locale.ENGLISH, "%d.%d.%d.%d", (IP & 0xff),(IP >> 8 & 0xff),(IP >> 16 & 0xff),(IP >> 24 & 0xff)));
            NETNAME = wifiInf.getSSID();
            SSID.setText(NETNAME);
        } else {
            NETNAME = "";
            IPAddr.setText("[Disconnected]");
            SSID.setText("");
        }

        // TODO: nfc
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);

        if (adapter != null && adapter.isEnabled()) {
            Toast.makeText(this, "NFC is online!", Toast.LENGTH_LONG).show();
        } else {
            // something wrong
            Toast.makeText(this, "NFC is not available or disabled", Toast.LENGTH_LONG).show();
            P_RNGText.setText("NFC N/A");
        }

    }

    private void NFCLoop() {

    }
}
