package com.example.chitphentom.brocardsutil;

/**
 * Created by Chitphentom on 11/16/2016 AD.
 * Server Side Class
 */
import android.content.Context;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private ServerSocket serverSocket;
    private Context context;

    Server (Context context, int port) {
        super();
        this.context = context;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            serverSocket = null;
            Toast.makeText(context, "Couldn't listen to the port", Toast.LENGTH_LONG).show();
        }
    }

    public boolean success() {
        return serverSocket != null;
    }

    @Override
    public void run() {
        Socket socket = null;
        while (true) {
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                Toast.makeText(context, "Couldn't listen to the port", Toast.LENGTH_LONG).show();
                System.exit(-1);
            }
        }
    }
}
