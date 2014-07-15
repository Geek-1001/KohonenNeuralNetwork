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

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int clusterNumber = 5;
        int inputNumber = 3;
        KohonenNetworkBuilder networkBuilder = new KohonenNetworkBuilder(clusterNumber, inputNumber);
        networkBuilder.setEdgesWeightList();


        Log.d("TAG", "Learning Vector");
        for(double[] learningVector : networkBuilder.getEdgesWeightList()){
            for(int i = 0; i < learningVector.length; ++i){
                Log.d("TAG", " | " + learningVector[i]);
            }
            Log.d("TAG", "\n");
            Log.d("TAG", "\n");
        }


        double[] inputSignal = new double[]{4, 3, 2};
        KohonenNetwork network = new KohonenNetwork(this, networkBuilder);
        network.setInputSignal(inputSignal);
        double[] outputSignal = network.getOutputSignal();


        for(int i = 0; i < outputSignal.length; ++i){
            Log.d("TAG", " | " + outputSignal[i]);
        }

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
