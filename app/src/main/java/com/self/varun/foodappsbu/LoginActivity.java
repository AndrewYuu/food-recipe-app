package com.self.varun.foodappsbu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;

public class LoginActivity extends AppCompatActivity {

    static int PIC_CAPTURED = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       Button loginBtn = (Button) findViewById(R.id.push_button) ;
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, PIC_CAPTURED);
                }

//                try {
//                    URL myURL = new URL("http://example.com/");
//                    new QueryTask(LoginActivity.this).execute(myURL);
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == PIC_CAPTURED && resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            Bitmap bmp = (Bitmap) extras.get("data");
            try {
                getIngredients(bmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getIngredients(Bitmap picture) throws IOException {

        ClarifaiClient client = new ClarifaiBuilder("6f50942cdbf14c6db613b41e291c5507").buildSync();

        File imageFile = new File(getFilesDir(), "picture");
        OutputStream os = new BufferedOutputStream(new FileOutputStream(imageFile));
        picture.compress(Bitmap.CompressFormat.JPEG, 100, os);
        os.close();

        new ImageRecogTask(imageFile, this).execute();

//        final List<ClarifaiOutput<Concept>> predictionResults = client.getDefaultModels().generalModel() // You can also do client.getModelByID("id") to get your custom models
//                .predict()
//                .withInputs(
//                        ClarifaiInput.forImage(file))
//                .executeSync()
//                .get();
//
//        setContentView(R.layout.activity_main);
//
//        TextView ingredientsView = (TextView)findViewById(R.id.ingredientsView);
//        ClarifaiOutput<Concept> concept = predictionResults.get(0);
//        List<Concept> concepts = concept.data();
////        for(Concept c : concepts) {
////            System.out.println(c.name());
////        }
//        ingredientsView.setText(concepts.get(0).name());
//        setContentView(R.layout.activity_main);
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
