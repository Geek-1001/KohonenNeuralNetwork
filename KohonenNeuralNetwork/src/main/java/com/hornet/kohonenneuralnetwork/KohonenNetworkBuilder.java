package com.hornet.kohonenneuralnetwork;

/**
 * Created by Ahmed on 5/17/14.
 */
public class KohonenNetworkBuilder {

// #MARK - Constants

    //public static final int
    private int clustersNumber;
    private int inputsNumber;
    private boolean isRandomEdgesWeight;

    private double[] edgesWeightArray;

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
        this.isRandomEdgesWeight = true;
        this.edgesWeightArray = null;
    }

// #MARK - Custom Methods

    // #MARK - Setters

    public void setRandomEdgesWeight(boolean state){
        this.isRandomEdgesWeight = state;
    }

    public void setClustersNumber(int clustersNumber){
        this.clustersNumber = clustersNumber;
    }

    public void setInputsNumber(int inputsNumber){
        this.inputsNumber = inputsNumber;
    }

    public void setEdgesWeightArray(double[] weight){
        this.edgesWeightArray = weight;
    }

    // #MARK - Getters

    public double[] getEdgesWeightArray(){
        return this.edgesWeightArray;
    }

    public int getInputsNumber(){
        return this.inputsNumber;
    }

    public int getClustersNumber(){
        return this.clustersNumber;
    }

    public boolean isRandomEdgesWeight(){
        return this.isRandomEdgesWeight;
    }

}
