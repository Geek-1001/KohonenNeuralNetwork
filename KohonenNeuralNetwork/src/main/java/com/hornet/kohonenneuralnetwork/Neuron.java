package com.hornet.kohonenneuralnetwork;

/**
 * Created by Ahmed on 5/18/14.
 * Basic class for creating all types of Neurons
 *
 * @author Ahmed Sulaiman
 * @version 0.0.1
 */
public abstract class Neuron {

// #MARK - Properties

    protected Edge[] inputEdges;
    protected Edge outputEdge;

// #MARK - Custom methods

    // #MARK - REQUIRED METHODS

    /**
     * Activation function
     *
     * @return signal in output edge of neuron
     */
    public abstract double getOutputSignal();

    // #MARK - OPTIONAL METHODS (utility)

    /**
     * Summing function
     *
     * @param inputEdges input edges into current neuron
     * @return NET value. Sum of multiply signal from every edge to weight from every edge
     */
    protected double getNetValue(Edge[] inputEdges){
        double netValue = 0;
        double[] signal = getInputSignal(inputEdges);
        double[] weight = getWeight(inputEdges);
        for(int i = 0; i < inputEdges.length; ++i){
           netValue += signal[i] * weight[i];
        }
        return netValue;
    }

    /**
     * @param inputEdges input edges into current neuron
     * @return array of all signals from every input edge connected to current neuron
     */
    protected double[] getInputSignal(Edge[] inputEdges){
        double[] signal = new double[inputEdges.length];
        for(int i = 0; i < inputEdges.length; ++i){
            signal[i] = inputEdges[i].getSignal();
        }
        return signal;
    }

    /**
     * @param inputEdges input edges into current neuron
     * @return array of all weights from every input edge connected to current neuron
     */
    protected double[] getWeight(Edge[] inputEdges){
        double[] weight = new double[inputEdges.length];
        for(int i = 0; i < inputEdges.length; ++i){
            weight[i] = inputEdges[i].getWeight();
        }
        return weight;
    }

}
