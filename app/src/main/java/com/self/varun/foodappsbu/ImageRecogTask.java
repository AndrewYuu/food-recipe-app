package com.self.varun.foodappsbu;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

/**
 * Created by Andrew on 9/15/2018.
 */

public class ImageRecogTask extends AsyncTask<URL, Integer, Long>{

    File imageFile;
    LoginActivity loginActivity;

    public ImageRecogTask(File imageFile, LoginActivity loginActivity){
        this.imageFile = imageFile;
        this.loginActivity = loginActivity;
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    @Override
    protected Long doInBackground(URL... urls) {

        ClarifaiClient client = new ClarifaiBuilder("6f50942cdbf14c6db613b41e291c5507").buildSync();

        final List<ClarifaiOutput<Concept>> predictionResults = client.getDefaultModels().foodModel() // You can also do client.getModelByID("id") to get your custom models
                .predict()
                .withInputs(
                        ClarifaiInput.forImage(imageFile))
                .executeSync()
                .get();


        ClarifaiOutput<Concept> concept = predictionResults.get(0);
        final List<Concept> concepts = concept.data();
//        for(Concept c : concepts) {
//            System.out.println(c.name());
//        }

        loginActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Stuff that updates the UI
                TextView ingredientsView = (TextView)loginActivity.findViewById(R.id.captionView);
                ingredientsView.setText(concepts.get(0).name());
//                mainActivity.setContentView(R.layout.activity_main);
            }
        });


        return Long.valueOf(1);
    }

    protected void onPostExecute(Long result) {

    }

}
