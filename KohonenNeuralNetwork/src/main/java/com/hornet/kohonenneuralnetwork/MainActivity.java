package com.hornet.kohonenneuralnetwork;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    KohonenNetwork network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int clusterNumber = 5;
        int inputNumber = 3;
        KohonenNetworkBuilder networkBuilder = new KohonenNetworkBuilder(clusterNumber, inputNumber);


        List<double[]> weights = new ArrayList<double[]>();
        weights.add(new double[]{20, 8, 1});
        weights.add(new double[]{2, 1, 1});
        weights.add(new double[]{5, 8, 20});
        weights.add(new double[]{0, 20, 1});
        weights.add(new double[]{20, 10, 20});

        networkBuilder.setEdgesWeightList(weights);
//        networkBuilder.setEdgesWeightList();


        TextView log = (TextView) findViewById(R.id.logOutput);
        log.setText("");
        for(double[] items : networkBuilder.getEdgesWeightList()){
            log.append("ITEM : \n");
            for(double value : items){
                log.append(value + "\n");
            }
            log.append("\n\n");
        }

        network = new KohonenNetwork(this, networkBuilder);

    }

    public void onClick(View view) {

        EditText input_first = (EditText) findViewById(R.id.input_1);
        EditText input_second = (EditText) findViewById(R.id.input_2);
        EditText input_third = (EditText) findViewById(R.id.input_3);
        TextView output = (TextView) findViewById(R.id.output);

        double[] inputSignal = new double[]{Double.parseDouble(input_first.getText().toString()), Double.parseDouble(input_second.getText().toString()), Double.parseDouble(input_third.getText().toString()) };

        network.setInputSignal(inputSignal);

        double[] outputSignal = network.getOutputSignal();

        output.setText("");
        for(double value : outputSignal){
            output.append(" | " + value);
        }
        output.append("\n\n" + System.currentTimeMillis());
    }

    public void onLearn(View view) {

        KohonenNetworkLearningBuilder learningBuilder = new KohonenNetworkLearningBuilder();
        learningBuilder.setLearningEraCount(2);
        learningBuilder.setLearningNorm(0.8, 0.1, 0.1);
        learningBuilder.setUpdateRadius(5);

        List<double[]> learningVectors = new ArrayList<double[]>();
        Random random = new Random();
        for(int i = 0; i < 100; i++) {
            double[] currentLearningVector = new double[3];
            for(int j = 0; j < currentLearningVector.length; ++j) {
                currentLearningVector[j] = (double) random.nextInt(500);
            }
            learningVectors.add(currentLearningVector);
        }
        learningBuilder.setLearningVectors(learningVectors);

        network.startLearning(learningBuilder);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
