package com.example.chitphentom.brocardsutil;

import android.os.AsyncTask;
import android.util.JsonReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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
    private URL url;
    private boolean posted = false;
    private InputStream inputStream;

    Client (int destinationIP, int port) {
        try {
            this.url = new URL("http://" + num_to_url(destinationIP, port) + "/");
        } catch (Exception e) {
            this.url = null;
        }
    }

    Client (String url) {
        try {
            this.url = new URL(url);
        } catch (Exception e) {
            this.url = null;
        }
    }

    public boolean addData() {
        return false;
    }

    public boolean post(String msg) {
        if (this.url == null) return false;

        URLConnection connection;

        try {
            connection = url.openConnection();
        } catch (Exception e) {
            return false;
        }

        HttpURLConnection httpPost = (HttpURLConnection) connection;

        try {
            httpPost.setDoOutput(true);
            httpPost.setChunkedStreamingMode(0);

            httpPost.setRequestMethod("POST");
            httpPost.setRequestProperty("Content-type", "application/json");

            OutputStream out = new BufferedOutputStream(httpPost.getOutputStream());

            // post here
            out.write(msg.getBytes());

            // get response
            inputStream = new BufferedInputStream(httpPost.getInputStream());

            posted = true;
        } catch (Exception e) {
            // fail, return false
            httpPost.disconnect();
            return false;
        } finally {
            // disconnect
            httpPost.disconnect();
        }


        return true;
    }

    public String getResponse() {
        if (posted) {
            Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } else {
            return null;
        }
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
