package com.hornet.kohonenneuralnetwork;

/**
 * Created by Ahmed on 5/17/14.
 */
public class KohonenNetworkBuilder {

// #MARK - Constants

    //public static final int
    private int clustersNumber;
    private int inputsNumber;
    private boolean isRandomClustersPrototypes;
    private boolean isRandomEdgesWeight;

    private double[] edgesWeightArray;

// #MARK - Constructors

    KohonenNetworkBuilder(){
        this.clustersNumber = 0;
        this.inputsNumber = 0;
        this.isRandomEdgesWeight = true;
        this.isRandomClustersPrototypes = true;
    }

    KohonenNetworkBuilder(int clustersNumber, int inputsNumber){
        this.clustersNumber = clustersNumber;
        this.inputsNumber = inputsNumber;
        this.isRandomEdgesWeight = true;
        this.isRandomClustersPrototypes = true;
    }

// #MARK - Custom Methods

    public void setRandomClustersPrototypes(boolean state){
        this.isRandomClustersPrototypes = state;
    }

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

}
