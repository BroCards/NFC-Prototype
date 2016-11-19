package com.example.chitphentom.nfcprototype;

import android.nfc.NdefRecord;

/**
 * Nfcletter
 * use to pack the message - send and receive the nfc thing
 * Created by Chitphentom on 11/1/2016 AD.
 */

class NFCLetter {
    private int ip_addr = 0;
    private String ssid = null, RNGText = null;
    private boolean initialized = false;

    // constructor
    NFCLetter (int ip_addr, String ssid, String RNGText) {
        this.ip_addr = ip_addr;
        this.ssid = ssid;
        this.initialized = true;
        this.RNGText = RNGText;
    }

    // pack_helper: pack bytes to send
    private NdefRecord pack_helper(byte[] payload, byte[] id) {
        if (id == null) id = new byte[0];
        return new NdefRecord(
                NdefRecord.TNF_WELL_KNOWN,
                NdefRecord.RTD_TEXT,
                id,
                payload
        );
    }

    // pack: bundle messages
    NdefRecord[] pack() {
        if (!initialized) {
            return null;
        }
        NdefRecord[] letter = new NdefRecord[3];

        letter[0] = pack_helper(ssid.getBytes(), new byte[] {(byte) 0});
        letter[1] = pack_helper(String.valueOf(ip_addr).getBytes(), new byte[] {(byte) 1});
        letter[2] = pack_helper(RNGText.getBytes(), new byte[] {(byte) 2});
        return letter;
    }

    String getSSID() {return ssid;}

    int getIP() {return ip_addr;}

    boolean isInitialized() {return initialized;}

    String getRNGText() {return RNGText;}

}
