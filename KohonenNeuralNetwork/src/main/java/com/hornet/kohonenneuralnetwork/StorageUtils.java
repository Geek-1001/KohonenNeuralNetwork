package com.hornet.kohonenneuralnetwork;

import android.content.Context;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ahmed on 6/10/14.
 */
public class StorageUtils {

    private static final String FILENAME = "inputEdgesWeight.txt";

//    public static void writeInputEdgesWeightToFile(Context context, List<double[]> inputEdgesWeightList) throws IOException {
//        String lineSeparator = System.getProperty("line.separator");
//        OutputStreamWriter outputStreamWriter = null;
//        try {
//            outputStreamWriter = new OutputStreamWriter(context.openFileOutput(FILENAME, Context.MODE_PRIVATE));
//            outputStreamWriter = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
//            outputStreamWriter.write("");
//
//            for(double[] weight : inputEdgesWeightList){
//                for(int i = 0; i < weight.length; ++i){
//                    outputStreamWriter.append(weight[i] + " ");
//                }
//                outputStreamWriter.append(lineSeparator);
//            }
//        } finally {
//            outputStreamWriter.flush();
//            outputStreamWriter.close();
//        }
//    }

    public static void writeInputEdgesWeightToFile(Context context, List<double[]> inputEdgesWeightList) throws IOException {
        String lineSeparator = System.getProperty("line.separator");
        FileOutputStream outputStream = null;
        try {
            outputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);

            for(double[] weight : inputEdgesWeightList){
                for(int i = 0; i < weight.length; ++i){
                    String toWrite;
                    if(i == weight.length - 1) {
                        toWrite = String.valueOf(weight[i]);
                    } else {
                        toWrite = weight[i] + " ";
                    }
                    outputStream.write(toWrite.getBytes());
                }
                outputStream.write(lineSeparator.getBytes());
            }

        } finally {
            outputStream.flush();
            outputStream.close();
        }
    }

    // TODO: fix reading bug!!!
    public static List<double[]> getInputEdgesWeightFromFile(Context context) {
        List<double[]> inputEdgesWeightList = new ArrayList<double[]>();
        try{
            FileInputStream fileInputStream = context.openFileInput(FILENAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line != null) {
                String[] lineArray = line.split(" ");
                double[] weightArray = new double[lineArray.length];
                for(int i = 0; i < lineArray.length; ++i){
                    weightArray[i] = Double.parseDouble(lineArray[i]);
                }
                inputEdgesWeightList.add(weightArray);
                line = bufferedReader.readLine();
            }

            fileInputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        }catch(FileNotFoundException exception){
            return null;
        }catch(IOException exception){
            exception.printStackTrace();
        }

        return inputEdgesWeightList;
    }

    // TODO: rename this method
    public static boolean isFileExists(Context context){
        File file = new File(context.getFilesDir(), FILENAME);
        return file.exists();
    }

    public static boolean isRememberedClusterCountCorrect(int clusterNeuronCount, Context context){
        List<double[]> inputEdgesWeightList = getInputEdgesWeightFromFile(context);
        if(inputEdgesWeightList.size() == clusterNeuronCount){
            return true;
        }
        return false;
    }

    public static boolean isRememberedInputCountCorrect(int inputEdgesCount, Context context){
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
