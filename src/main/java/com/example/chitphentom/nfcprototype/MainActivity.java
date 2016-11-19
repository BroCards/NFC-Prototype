package com.example.chitphentom.nfcprototype;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiInfo;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

// buttons
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

// toasts
import android.widget.Toast;

import com.example.chitphentom.brocardsutil.RNGText;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
    implements NfcAdapter.OnNdefPushCompleteCallback,
                NfcAdapter.CreateNdefMessageCallback {

    Button Refresh;
    TextView RNGField, IPAddr, SSID, P_IP, P_SSID, P_RNGText;
    String rngStr;
    NfcAdapter adapter;

    // network information
    private int IP;
    private String NETNAME;

    private int partner_ip;
    private String partner_SSID;
    private String partner_rngStr;

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

        // nfc
        adapter = NfcAdapter.getDefaultAdapter(this);

        refresh();
    }

    @Override
    public void onNdefPushComplete(NfcEvent event) {
        Toast.makeText(this, "message sent!", Toast.LENGTH_LONG).show();
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        // called when another nfc capable device is in range
        NFCLetter letter = new NFCLetter(IP, NETNAME, rngStr);
        return new NdefMessage(letter.pack());
    }

    @Override
    public void onNewIntent(Intent intent) {
        handleNFC(intent);
    }

    // handle nfc
    private void handleNFC(Intent nfcIntent) {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(nfcIntent.getAction())) {
            Parcelable[] receivedArray = nfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (receivedArray == null) {
                Toast.makeText(this, "received blank package", Toast.LENGTH_LONG).show();
            } else {
                NdefMessage receivedMsg = (NdefMessage) receivedArray[0];
                NdefRecord[] attachments = receivedMsg.getRecords();
                for (NdefRecord r:attachments) {
                    switch (r.getId()[0]) {
                        case 0:
                            // ssid
                            P_SSID.setText(new String(r.getPayload()));
                            break;
                        case 1:
                            // ip
                            int ip = Integer.valueOf(new String(r.getPayload()));
                            P_IP.setText(String.format(Locale.ENGLISH, "%d.%d.%d.%d", (IP & 0xff),
                                    (IP >> 8 & 0xff),(IP >> 16 & 0xff),(IP >> 24 & 0xff)));
                        case 2:
                            // rng msgs
                            P_RNGText.setText(new String(r.getPayload()));
                            break;
                        default:
                            Toast.makeText(this, "Package has invalid id", Toast.LENGTH_LONG).show();
                    }
                }
            }
            Refresh.setText("Clear & Refresh");
        }
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
            IPAddr.setText(String.format(Locale.ENGLISH, "%d.%d.%d.%d", (IP & 0xff),
                    (IP >> 8 & 0xff),(IP >> 16 & 0xff),(IP >> 24 & 0xff)));
            NETNAME = wifiInf.getSSID();
            SSID.setText(NETNAME);
        } else {
            NETNAME = "";
            IPAddr.setText("[Disconnected]");
            SSID.setText("");
        }

        // clear fields
        P_SSID.setText("");
        P_RNGText.setText("");
        P_IP.setText("");

        if (adapter != null && adapter.isEnabled()) {
            adapter.setNdefPushMessageCallback(this, this);
            adapter.setOnNdefPushCompleteCallback(this, this);
        } else {
            // something wrong
            P_IP.setText("[NFC offline]");
        }

    }
}
