package com.hornet.kohonenneuralnetwork;

/**
 * Created by Ahmed on 5/18/14.
 * Class for cluster neuron object.
 *
 * @author Ahmed Sulaiman
 * @version 0.0.1
 */
public class ClusterNeuron extends Neuron {

// #MARK - Constants

    private double[] clusterPrototype;
    private double[] currentSignal;

// #MARK - Constructors

    /**
     * Default cluster neuron constructor.
     */
    ClusterNeuron(){
        init(null, null);
    }

    /**
     * Cluster neuron constructor with parameters.
     *
     * @param inputEdges array of input edges into current cluster neuron
     * @param outputEdge output edge from current neuron
     */
    ClusterNeuron(Edge[] inputEdges, Edge outputEdge){
        init(inputEdges, outputEdge);
    }

    private void init(Edge[] inputEdges, Edge outputEdge){
        this.inputEdges = inputEdges;
        this.outputEdge = outputEdge;
        this.clusterPrototype = this.getWeight(inputEdges);
        this.currentSignal = this.getInputSignal(inputEdges);
    }

// #MARK - Override methods

    /**
     * Method for getting output signal from current
     * cluster neuron.
     *
     * @see Neuron#getOutputSignal()
     * @return Euclidean distance from cluster prototype to current signal
     */
    @Override
    public double getOutputSignal(){
        return getEuclideanDistance(this.currentSignal);
    }

// #MARK - Custom methods

    /**
     * Set output signal for output edge in current cluster neuron.
     *
     * @throws java.lang.IllegalArgumentException Output signal should be only 1 or 0;
     * @param signal output signal in output edge.
     */
    public void setOutputSignal(double signal){
        if(signal == 0 || signal == 1){
            this.outputEdge.setSignal(signal);
        } else {
            throw new IllegalArgumentException("Cluster output signal should be 1 or 0");
        }
    }

    /**
     * Method for getting Euclidean distance value from cluster prototype to signal.
     *
     * @param currentSignal signal for getting distance to it
     * @return Euclidean distance
     */
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
