package com.example.dicoccojokelab;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class JokeViewModel extends AndroidViewModel {

    private MutableLiveData<String> resultJSON;


    public JokeViewModel(Application application) {
        super(application);
    }

    public MutableLiveData<String> getResultJSON() {
        if (resultJSON == null) {
            resultJSON = new MutableLiveData<String>();
        }
        return resultJSON;
    }

    public void loadData() {
        AsyncTask<Void,Void, String > asyncTask = new AsyncTask<Void, Void, String>() {

            @Override

            protected String doInBackground(Void... voids) {
                HttpsURLConnection urlConnection = null;
                String result = "";
                try{
                    String urlString = "https://official-joke-api.appspot.com/random_joke";
                    URL url = new URL(urlString);
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    InputStream in = urlConnection.getInputStream();

                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder total = new StringBuilder();
                    for (String line; (line = r.readLine()) != null; ) {
                        total.append(line).append('\n');
                    }
                    result=total.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection!= null){
                        urlConnection.disconnect();
                    }
                }

                return result;
            }

            @Override
            protected void onPostExecute(String result) {

                resultJSON.setValue(result);

            };
        };
        asyncTask.execute();
    }
}
