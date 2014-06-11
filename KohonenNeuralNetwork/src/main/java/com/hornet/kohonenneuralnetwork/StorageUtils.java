package com.hornet.kohonenneuralnetwork;

import android.content.Context;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmed on 6/10/14.
 */
public class StorageUtils {

    private static final String FILENAME = "inputEdgesWeight.txt";

    public static void writeInputEdgesWeightToFile(Context context, List<double[]> inputEdgesWeightList) throws IOException {
        String lineSeparator = System.getProperty("line.separator");
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_PRIVATE));
            outputStreamWriter.write("");

            for(double[] weight : inputEdgesWeightList){
                for(int i = 0; i < weight.length; ++i){
                    outputStreamWriter.append(weight[i] + " ");
                }
                outputStreamWriter.append(lineSeparator);
            }
        } finally {
            outputStreamWriter.flush();
            outputStreamWriter.close();
        }
    }

    public static List<double[]> getInputEdgesWeightFromFile(Context context) throws IOException {
        List<double[]> inputEdgesWeightList = new ArrayList<double[]>();
        FileInputStream fileInputStream = new FileInputStream(FILENAME);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
        String line;
        while((line = bufferedReader.readLine()) != null) {
            String[] lineArray = line.split(" ");
            double[] weightArray = new double[lineArray.length];
            for(int i = 0; i < lineArray.length; ++i){
                weightArray[i] = Double.parseDouble(lineArray[i]);
            }
            inputEdgesWeightList.add(weightArray);
        }
        return inputEdgesWeightList;
    }

    public static boolean isRememberedClusterCountCorrect(Context context, int clusterNeuronCount) throws IOException {
        List<double[]> inputEdgesWeightList = getInputEdgesWeightFromFile(context);
        if(inputEdgesWeightList.size() == clusterNeuronCount){
            return true;
        }
        return false;
    }

    public static boolean isRememberedInputCountCorrect(Context context, int inputEdgesCount) throws IOException{
        List<double[]> inputEdgesWeightList = getInputEdgesWeightFromFile(context);
        boolean state = false;
        for(double[] weight : inputEdgesWeightList){
            state = false;
            if(weight.length == inputEdgesCount){
                state = true;
            }
        }
        return state;
    }

}
