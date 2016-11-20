package com.example.chitphentom.brocardsutil;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

/** simple reply dependency
 *  delete this when no needed */
import org.json.JSONException;
import org.json.JSONObject;
/***************************/

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Chitphentom on 11/16/2016 AD.
 * Server Side Class
 */


public class Server extends Thread {
    private int PORT;
    private ServerSocket serverSocket;
    static String _TAG_ = "SERVER";
    private int counter;
    TextView counterView;


    Server(TextView textView, int port) {
        super();

        PORT = port;
        counter = 0;
        this.counterView = textView;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                SimpleReplyThread simpleReplyThread = new SimpleReplyThread(socket, counter);

                // reply
                simpleReplyThread.start();

                // increment
                counter++;

                // Set ui
                counterView.setText("" + counter);
            }


        } catch (IOException e) {
            // Catch block
            e.printStackTrace();
        }
    }

    public int getCounter() {
        return this.counter;
    }


}

/** Instead of Table this is the simple reply thread that simply reply some
 *  JSON object that simulate the state of the device that it is in.
 */

class SimpleReplyThread extends Thread {
    private Socket socket;
    private String jsonStr;

    SimpleReplyThread(Socket socket, int playerNum) {
        this.socket = socket;
        JSONObject simpleJSON = new JSONObject();

        // json
        try {
            simpleJSON.accumulate("Status", "Connected");
            simpleJSON.accumulate("PlayerNo", playerNum);
            jsonStr = simpleJSON.toString();

            // logging
            Log.d(Server._TAG_, "json string: " + jsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            PrintStream out = new PrintStream(socket.getOutputStream());
            out.print(jsonStr);
            out.close();

            // close socket maybe ??
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
