package com.self.varun.foodappsbu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText usertxt = (EditText)findViewById(R.id.usrTxt);
        EditText passtxt = (EditText)findViewById(R.id.pwdTxt);
        Button loginBtn = (Button) findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    URL myURL = new URL("http://example.com/");
                    new QueryTask().execute(myURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });


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
