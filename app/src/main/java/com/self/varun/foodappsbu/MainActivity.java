package com.self.varun.foodappsbu;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import java.util.List;

/**
 * Created by Andrew on 9/14/2018.
 */

public class MainActivity {
    ClarifaiClient client = new ClarifaiBuilder("6f50942cdbf14c6db613b41e291c5507").buildSync();

    final List<ClarifaiOutput<Concept>> predictionResults = client.getDefaultModels().generalModel() // You can also do client.getModelByID("id") to get your custom models
            .predict()
            .withInputs(
                    ClarifaiInput.forImage("https://samples.clarifai.com/metro-north.jpg"))
            .executeSync()
            .get();

    
}
