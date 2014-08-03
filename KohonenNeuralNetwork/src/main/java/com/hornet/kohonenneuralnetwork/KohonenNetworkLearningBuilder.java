package com.hornet.kohonenneuralnetwork;

import java.util.List;

/**
 * Created by Ahmed on 5/24/14.
 */
public class KohonenNetworkLearningBuilder {

// #MARK - Constants

    // DEFAULT VALUES
    public static final int DEFAULT_ERA_COUNT = 4;
    public static final double DEFAULT_LEARNING_NORM_FROM = 0.8;
    public static final double DEFAULT_LEARNING_NORM_TO = 0.1;
    public static final double DEFAULT_LEARNING_NORM_DECREMENT_STEP_VALUE = 0.1;

    // PROPERTIES
    private int learningEraCount;
    private double learningNormFrom;
    private double learningNormTo;
    private double learningNormDecrementStepValue;
    private int updateRadius;
    private List<double[]> learningVectors;

// #MARK - Constructors

    public KohonenNetworkLearningBuilder(){
        init(DEFAULT_ERA_COUNT, DEFAULT_LEARNING_NORM_FROM, DEFAULT_LEARNING_NORM_TO, DEFAULT_LEARNING_NORM_DECREMENT_STEP_VALUE, 1, null);
    }

    public KohonenNetworkLearningBuilder(int learningEraCount, double learningNormFrom, double learningNormTo, double learningNormDecrementStepValue, int clusterCount, List<double[]> learningVectors){
        init(learningEraCount, learningNormFrom, learningNormTo, learningNormDecrementStepValue, clusterCount, learningVectors);
    }

    private void init(int learningEraCount, double learningNormFrom, double learningNormTo, double learningNormDecrementStepValue, int clusterCount, List<double[]> learningVectors){
        this.learningEraCount = learningEraCount;
        this.learningNormFrom = learningNormFrom;
        this.learningNormTo = learningNormTo;
        this.learningNormDecrementStepValue = learningNormDecrementStepValue;
        this.learningVectors = learningVectors;
        this.updateRadius = getUpdateRadius(clusterCount);
    }

// #MARK - Custom Methods

    public void setLearningEraCount(int eraCount){
        this.learningEraCount = eraCount;
    }

    public int getLearningEraCount(){
        return this.learningEraCount;
    }

    public void setLearningNorm(double from, double to, double decrementStepValue){
        this.learningNormFrom = from;
        this.learningNormTo = to;
        this.learningNormDecrementStepValue = decrementStepValue;
    }

    public double getLearningNormFrom(){
        return this.learningNormFrom;
    }

    public double getLearningNormTo(){
        return this.learningNormTo;
    }

    public double getLearningNormDecrementStepValue(){
        return this.learningNormDecrementStepValue;
    }

    public void setUpdateRadius(int clusterCount){
        this.updateRadius = getUpdateRadius(clusterCount);
    }

    public int getUpdateRadius(){
        return this.updateRadius;
    }

    private int getUpdateRadius(int clusterCount){
        return Math.abs(Math.round(clusterCount/2) - 1);
    }

    public void setLearningVectors(List<double[]> learningVectors){
        this.learningVectors = learningVectors;
    }

    public List<double[]> getLearningVectors(){
        return this.learningVectors;
    }

}
