package com.example.chitphentom.brocardsutil;

import java.net.Socket;

/**
 * Created by Chitphentom on 11/16/2016 AD.
 */

class ServerHelper extends Thread {
    private Socket socket;
    private int player_id;

    ServerHelper(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

    }

}
