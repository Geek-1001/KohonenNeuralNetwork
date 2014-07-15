package com.hornet.kohonenneuralnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Ahmed on 5/17/14.
 */
public class KohonenNetworkBuilder {

// #MARK - Constants

    private int clustersNumber;
    private int inputsNumber;
    private List<double[]> edgesWeightList;

// #MARK - Constructors

    KohonenNetworkBuilder(){
        init(0, 0);
    }

    KohonenNetworkBuilder(int clustersNumber, int inputsNumber){
        init(clustersNumber, inputsNumber);
    }

    private void init(int clustersNumber, int inputsNumber){
        this.clustersNumber = clustersNumber;
        this.inputsNumber = inputsNumber;
        this.edgesWeightList = null;
    }

// #MARK - Custom Methods

    public void setClustersNumber(int clustersNumber){
        this.clustersNumber = clustersNumber;
    }

    public void setInputsNumber(int inputsNumber){
        this.inputsNumber = inputsNumber;
    }

    public void setEdgesWeightList(List<double[]> weight){
        this.edgesWeightList = weight;
    }

    public void setEdgesWeightList(){
        this.edgesWeightList = getRandomInputEdgesWeight(this.inputsNumber, this.clustersNumber);
    }

    public List<double[]> getEdgesWeightList(){
        return this.edgesWeightList;
    }

    public int getInputsNumber(){
        return this.inputsNumber;
    }

    public int getClustersNumber(){
        return this.clustersNumber;
    }

    private List<double[]> getRandomInputEdgesWeight(int inputsNumber, int clustersNumber){
        List<double[]> edgesWeightList = new ArrayList<double[]>(clustersNumber);
        Random random = new Random();
        for(int i = 0; i < clustersNumber; ++i){
            double[] edgesWeight = new double[inputsNumber];
            for(int j = 0; j < edgesWeight.length; ++j){
                edgesWeight[j] = random.nextDouble();
            }
            edgesWeightList.add(edgesWeight);
        }
        return edgesWeightList;
    }


}
