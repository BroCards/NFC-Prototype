package com.example.chitphentom.brocardsutil;

import android.os.AsyncTask;
import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.Scanner;

/**
 * Client.java
 * The class for handle connection between the player and table
 * Designed for making POST connection in general
 * Created by Settasit Chitphentom on 11/16/2016 AD.
 */

public class Client {
    private boolean posted = false;
    private InputStream inputStream;
    private String destIP;
    private int port;
    private JSONObject responseObj;

    Client (String destinationIP, int port) {
        destIP = destinationIP;
        this.port = port;
    }

    public boolean postData(String str) {
        Socket socket = null;
        try {
            socket = new Socket(destIP, port);

            PrintStream out = new PrintStream(socket.getOutputStream());
            out.print(str);

            // response
            JsonReader reader = new JsonReader(new InputStreamReader(socket.getInputStream()));

            // read!
            reader.beginObject();

            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("Status")) {
                    return (reader.nextString().equals("Connected"));
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }




    /** Utilities function(s):
     *  might migrate this chunk to another class :v
     *  but they are here... for now.
     */
    public static String num_to_url(int IP, int port) {
        return String.format(Locale.getDefault(),"%d.%d.%d.%d:%d",
                (IP & 0xff),
                (IP >> 8) & 0xff,
                (IP >> 16) & 0xff,
                (IP >> 24) & 0xff,
                port);
    }

}
