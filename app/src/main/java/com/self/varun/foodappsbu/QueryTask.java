package com.self.varun.foodappsbu;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.Console;
import java.io.IOException;
import java.net.URL;

class QueryTask extends AsyncTask<URL, Integer, Long> {
    LoginActivity login;
    String text;

    public QueryTask(LoginActivity context)
    {
     this.login = context;
    }
    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected Long doInBackground(URL... urls) {
        final TextView txt = (TextView) login.findViewById(R.id.captionView);
        try {
            text = getFoodID("carrot");
        } catch (IOException e) {
            e.printStackTrace();
        }

        login.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                // Stuff that updates the UI
                txt.setText(text);

            }
        });

        return Long.valueOf(1);

    }

    protected void onPostExecute(Long result) {

    }
    public static String getFoodID(String query) throws IOException {
        String url = "https://ndb.nal.usda.gov/ndb/search/list?qlookup=" + query;
        Document doc = Jsoup.connect(url).userAgent("Jsoup client").timeout(500000).get();
        Element div = doc.selectFirst("div[class=list-left]");
        Element td = div.select("td").get(1);
        Element a = td.selectFirst("a");
        String text = a.text();
        return text;
    }
}


