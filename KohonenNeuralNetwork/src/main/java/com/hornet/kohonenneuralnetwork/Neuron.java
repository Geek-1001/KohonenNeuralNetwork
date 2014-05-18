package com.hornet.kohonenneuralnetwork;

/**
 * Created by Ahmed on 5/18/14.
 */
public abstract class Neuron {

// #MARK - Properties

    protected Edge[] inputEdges;
    protected Edge outputEdge;

// #MARK - Custom methods

    // #MARK - REQUIRED METHODS

    public abstract double getOutputSignal();

    // #MARK - OPTIONAL METHODS (utility)

    protected double getNetValue(Edge[] inputEdges){
        double netValue = 0;
        double[] signal = getInputSignal(inputEdges);
        double[] weight = getWeight(inputEdges);
        for(int i = 0; i < inputEdges.length; ++i){
           netValue += signal[i] * weight[i];
        }
        return netValue;
    }

    protected double[] getInputSignal(Edge[] inputEdges){
        double[] signal = new double[inputEdges.length];
        for(int i = 0; i < inputEdges.length; ++i){
            signal[i] = inputEdges[i].getSignal();
        }
        return signal;
    }

    protected double[] getWeight(Edge[] inputEdges){
        double[] weight = new double[inputEdges.length];
        for(int i = 0; i < inputEdges.length; ++i){
            weight[i] = inputEdges[i].getWeight();
        }
        return weight;
    }

}
