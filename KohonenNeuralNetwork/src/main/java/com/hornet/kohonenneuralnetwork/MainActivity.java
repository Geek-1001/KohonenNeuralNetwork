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

import java.util.Date;

public class MainActivity extends Activity {

    KohonenNetwork network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int clusterNumber = 5;
        int inputNumber = 3;
        KohonenNetworkBuilder networkBuilder = new KohonenNetworkBuilder(clusterNumber, inputNumber);
        networkBuilder.setEdgesWeightList();

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
            output.append(" " + value);
        }
        output.append("\n\n" + System.currentTimeMillis());
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
