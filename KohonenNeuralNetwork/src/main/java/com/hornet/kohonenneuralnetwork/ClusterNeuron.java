package com.hornet.kohonenneuralnetwork;

/**
 * Created by Ahmed on 5/18/14.
 */
public class ClusterNeuron extends Neuron {

// #MARK - Constants

    private double[] clusterPrototype;
    private double[] currentSignal;

// #MARK - Constructors

    ClusterNeuron(){
        this.inputEdges = null;
        this.outputEdge = null;

        this.clusterPrototype = null;
        this.currentSignal = null;
    }

    ClusterNeuron(Edge[] inputEdges, Edge outputEdge){
        this.inputEdges = inputEdges;
        this.outputEdge = outputEdge;

        this.clusterPrototype = this.getWeight(inputEdges);
        this.currentSignal = this.getInputSignal(inputEdges);
    }

// #MARK - Override methods

    @Override
    public double getOutputSignal(){
        return getEuclideanDistance(this.currentSignal);
    }

// #MARK - Custom methods

    public void setOutputSignal(double signal){
        this.outputEdge.setSignal(signal);
    }

    private double getEuclideanDistance(double[] currentSignal){
        double euclideanDistance = 0;
        if(currentSignal.length == clusterPrototype.length){
            for(int i = 0; i < currentSignal.length; ++i){
                euclideanDistance += Math.pow(currentSignal[i] - clusterPrototype[i], 2);
            }
        }
        return euclideanDistance;
    }

}
