package com.hornet.kohonenneuralnetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Ahmed on 5/18/14.
 */

public class KohonenNetwork {

// #MARK - Constants

    private List<Edge[]> inputEdges;
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
        if(inputSignal.length != this.inputEdges.size()){
            throw new IllegalArgumentException("Input signal length should be equal to input edges count");
        }
        int inputSignalIndex = 0;
        Iterator<Edge[]> inputEdgesIterator = inputEdges.iterator();
        while(inputEdgesIterator.hasNext()){
            Edge[] edges = inputEdgesIterator.next();
            for(Edge edge : edges){
                edge.setSignal(inputSignal[inputSignalIndex]);
            }
            inputSignalIndex++;
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

        List<double[]> edgesWeightList = networkBuilder.getEdgesWeightList();
        if(networkBuilder.isRandomEdgesWeight()){
            edgesWeightList = new ArrayList<double[]>(networkBuilder.getInputsNumber());
            Random random = new Random();
            for(int i = 0; i < edgesWeightList.size(); ++i){
                double[] edgesWeight = new double[networkBuilder.getClustersNumber()];
                for(int j = 0; j < edgesWeight.length; ++j){
                    edgesWeight[j] = random.nextDouble();
                }
                edgesWeightList.add(edgesWeight);
            }
        }
        buildInputEdges(networkBuilder.getInputsNumber(), networkBuilder.getClustersNumber(), edgesWeightList);
        buildOutputEdges(networkBuilder.getClustersNumber());
        buildClusterNeurons(networkBuilder.getClustersNumber());
    }

    private void buildInputEdges(int inputsCount, int clusterNeuronsCount, List<double[]> edgesWeightList) {
        this.inputEdges = new ArrayList<Edge[]>(inputsCount);
        for(int i = 0; i < this.inputEdges.size(); ++i){
            double[] currentWeightArray = edgesWeightList.get(i);
            Edge[] edges = new Edge[clusterNeuronsCount];
            for(int j = 0; j < edges.length; ++j){
                Edge inputEdge = new Edge(currentWeightArray[j], 0);
                edges[j] = inputEdge;
            }
            this.inputEdges.add(edges);
        }
    }

    private void buildClusterNeurons(int clusterNeuronsCount){
        this.clusters = new ClusterNeuron[clusterNeuronsCount];
        for(int i = 0; i < this.clusters.length; ++i){
            ClusterNeuron clusterNeuron = new ClusterNeuron(this.inputEdges.get(i), outputEdges[i]);
            this.clusters[i] = clusterNeuron;
        }
    }

    private void buildOutputEdges(int outputEdgesCount){
        this.outputEdges = new Edge[outputEdgesCount];
        for(int i = 0; i < outputEdgesCount; ++i){
            Edge outputEdge = new Edge(0, 0);
            this.outputEdges[i] = outputEdge;
        }
    }

// #MARK - Learning Methods



}
