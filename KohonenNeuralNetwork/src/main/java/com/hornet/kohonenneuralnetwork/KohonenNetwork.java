package com.hornet.kohonenneuralnetwork;

import java.util.Random;

/**
 * Created by Ahmed on 5/18/14.
 */
public class KohonenNetwork {

// #MARK - Constants

    private Edge[] inputEdges;
    private Edge[] outputEdges;
    private ClusterNeuron[] clusters;

    // TODO: create learning process
    private boolean isLearned = false;


// #MARK - Constructors

    KohonenNetwork(){
        buildNetwork(null);
    }

    KohonenNetwork(KohonenNetworkBuilder networkBuilder){
        buildNetwork(networkBuilder);
    }

// #MARK - Custom Methods

    public void setInputSignal(double[] inputSignal){
        if(inputSignal.length != this.inputEdges.length){
            throw new IllegalArgumentException("Input signal length should be equal to input edges count");
        }
        for(int i = 0; i < inputSignal.length; ++i){
            this.inputEdges[i].setSignal(inputSignal[i]);
        }
    }

    public double[] getOutputSignal(){
        double minimumEuclideanDistance = Integer.MAX_VALUE;
        int clusterWinnerIndex = 0;
        for(int i = 0; i < this.clusters.length; ++i){
            if(clusters[i].getOutputSignal() < minimumEuclideanDistance){
                minimumEuclideanDistance = clusters[i].getOutputSignal();
                clusterWinnerIndex = i;
            }
        }
        return getOutputSignalWithClusterWinner(clusterWinnerIndex);
    }

    private double[] getOutputSignalWithClusterWinner(int clusterWinnerIndex){
        double[] outputSignal = new double[this.clusters.length];
        int currentSignal;
        for(int i = 0; i < this.clusters.length; ++i){
            currentSignal = 0;
            if(i == clusterWinnerIndex){
                currentSignal = 1;
            }
            clusters[i].setOutputSignal(currentSignal);
            outputSignal[i] = currentSignal;
        }
        return outputSignal;
    }

// #MARK - Network Building Methods

    private void buildNetwork(KohonenNetworkBuilder networkBuilder){
        // TODO: rewrite with edges weight remember. With learning process.
        // TODO: Get remembered value of weight from this method

        if(networkBuilder == null){
            this.inputEdges = null;
            this.clusters = null;
            this.outputEdges = null;
            return;
        }

        // Create input edges
        double[] edgesWeightArray = networkBuilder.getEdgesWeightArray();
        if(networkBuilder.isRandomEdgesWeight()){
            edgesWeightArray = new double[networkBuilder.getInputsNumber()];
            Random random = new Random();
            for(int i = 0; i < networkBuilder.getInputsNumber(); ++i){
                edgesWeightArray[i] = random.nextDouble();
            }
        }
        this.inputEdges = buildInputEdges(networkBuilder.getInputsNumber(), edgesWeightArray);
        this.outputEdges = buildOutputEdges(networkBuilder.getClustersNumber());
        this.clusters = buildClusterNeurons(networkBuilder.getClustersNumber(), this.inputEdges, this.outputEdges);
    }

    private Edge[] buildInputEdges(int inputEdgesCount, double[] edgesWeightArray){
        Edge[] inputEdges = new Edge[inputEdgesCount];
        for(int i = 0; i < inputEdgesCount;){
            Edge inputEdge = new Edge();
            inputEdge.setSignal(0);
            inputEdge.setWeight(edgesWeightArray[i]);
            inputEdges[i] = inputEdge;
        }
        return inputEdges;
    }

    private ClusterNeuron[] buildClusterNeurons(int clusterNeuronsCount, Edge[] inputEdges, Edge[] outputEdges){
        ClusterNeuron[] clusters = new ClusterNeuron[clusterNeuronsCount];
        for(int i = 0; i < clusterNeuronsCount; ++i){
            ClusterNeuron clusterNeuron = new ClusterNeuron(inputEdges, outputEdges[i]);
            clusters[i] = clusterNeuron;
        }
        return clusters;
    }

    private Edge[] buildOutputEdges(int outputEdgesCount){
        Edge[] outputEdges = new Edge[outputEdgesCount];
        for(int i = 0; i < outputEdgesCount; ++i){
            Edge outputEdge = new Edge(0, 0);
            outputEdges[i] = outputEdge;
        }
        return outputEdges;
    }

// #MARK - Learning Methods



}
