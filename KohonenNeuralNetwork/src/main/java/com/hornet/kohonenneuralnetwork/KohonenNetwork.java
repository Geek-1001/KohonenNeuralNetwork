package com.hornet.kohonenneuralnetwork;

import java.util.Random;

/**
 * Created by Ahmed on 5/18/14.
 */
public class KohonenNetwork {

// #MARK - Constants

//    private KohonenNetworkBuilder networkBuilder;

    private Edge[] inputEdges;
    private Edge[] outputEdges;
    private ClusterNeuron[] clusters;


// #MARK - Constructors

    KohonenNetwork(){
//        this.networkBuilder = null;
    }

    KohonenNetwork(KohonenNetworkBuilder networkBuilder){
//        this.networkBuilder = networkBuilder;
    }

// #MARK - Custom Methods

    private void buildNetwork(KohonenNetworkBuilder networkBuilder){
        // TODO: rewrite with edges weight remember. With learning process.

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

        // Create output edges
        this.outputEdges = buildOutputEdges(networkBuilder.getClustersNumber());

        // Create clusters
        this.clusters = buildClusterNeurons(networkBuilder.getClustersNumber(), this.inputEdges, this.outputEdges);

    }

    private Edge[] buildInputEdges(int inputEdgeCount, double[] edgesWeightArray){
        return null;
    }

    private Edge[] buildOutputEdges(int outputEdgeCount){
        Edge[] outputEdges = new Edge[outputEdgeCount];
        for(int i = 0; i < outputEdgeCount; ++i){
            Edge outputEdge = new Edge(0, 0);
            outputEdges[i] = outputEdge;
        }
        return outputEdges;
    }

    private ClusterNeuron[] buildClusterNeurons(int clusterNeuronsCount, Edge[] inputEdges, Edge[] outputEdges){
        ClusterNeuron[] clusters = new ClusterNeuron[clusterNeuronsCount];
        for(int i = 0; i < clusterNeuronsCount; ++i){
            ClusterNeuron clusterNeuron = new ClusterNeuron(inputEdges, outputEdges[i]);
            clusters[i] = clusterNeuron;
        }
        return clusters;
    }

    public void setInputSignal(double[] inputSignal){
        if(inputSignal.length != this.inputEdges.length){
            throw new IllegalArgumentException("Input signal length should be equal to input edges count");
        }

    }

    public double getOutputSignalForCluster(int clusterIndex){
        if(clusterIndex > this.clusters.length || clusterIndex < 0){
            throw new IllegalArgumentException("Cluster index should be < cluster count");
        }

        return 0;
    }

    public double[] getOutputSignal(){

        return null;
    }



}
