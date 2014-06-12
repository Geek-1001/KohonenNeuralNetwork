package com.hornet.kohonenneuralnetwork;

import android.content.Context;

import java.io.IOException;
import java.io.OptionalDataException;
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

    private Context context;

// #MARK - Constructors

    KohonenNetwork(Context context){
        buildNetwork(context, null);
    }

    KohonenNetwork(Context context, KohonenNetworkBuilder networkBuilder){
        buildNetwork(context, networkBuilder);
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

    private void buildNetwork(Context context, KohonenNetworkBuilder networkBuilder){
        // TODO: rewrite with edges weight remember. With learning process.
        // TODO: Get remembered value of weight from this method

        if(networkBuilder == null || context == null){
            this.inputEdges = null;
            this.clusters = null;
            this.outputEdges = null;
            this.context = null;
            return;
        }

        this.context = context;

        List<double[]> edgesWeightList = networkBuilder.getEdgesWeightList();

        if(isLearned(context, networkBuilder.getClustersNumber(), networkBuilder.getInputsNumber())){
            edgesWeightList = getRestoredInputEdgesWeightList();
        }

        /*
        if(networkBuilder.isRandomEdgesWeight() && edgesWeightList == null){
            // TODO: make for this separate method; generateRandomInputEdgesWeight()
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
        */

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

// #MARK - Learning Methods // TODO: test all learning process
    // TODO: all globals variables set as parameters to methods instead of using in global scope

    public void startLearning(KohonenNetworkLearningBuilder learningBuilder){
        int updateRadius = learningBuilder.getUpdateRadius();
        double learningNorm = learningBuilder.getLearningNormFrom();

        for(int i = 0; i < learningBuilder.getLearningEraCount(); ++i){
            learn(learningBuilder.getLearningVectors(), updateRadius, learningNorm);
            updateRadius = getUpdatedRadius(updateRadius);
            learningNorm = getUpdatedLearningNorm(learningNorm, learningBuilder.getLearningNormTo(), learningBuilder.getLearningNormDecrementStepValue());
        }

        // TODO: save all edges weight to file for restoring this values in building process
        List<double[]> inputEdgesWeightList = getCurrentInputEdgesWeightList(this.inputEdges);
        saveNewInputEdgesWeight(inputEdgesWeightList);

    }

    private void learn(List<double[]> learningVectorsList, int updateRadius, double learningNorm){
        Iterator<double[]> learningVectorsIterator = learningVectorsList.iterator();
        while(learningVectorsIterator.hasNext()){
            double[] currentLearningVector = learningVectorsIterator.next();
            setInputSignal(currentLearningVector);

            int clusterWinnerIndex = getClusterWinnerIndex(this.clusters.length, this.clusters);
            updateClustersInputEdgesWeight(clusterWinnerIndex, learningNorm, currentLearningVector);
            OptimalClusterUpdatePosition optimalClusterUpdatePosition = getOptimalClusterUpdatePosition(updateRadius, this.clusters.length);
            updateClustersNearWinnerInputEdgesWeight(currentLearningVector, updateRadius, learningNorm, clusterWinnerIndex, this.clusters.length, optimalClusterUpdatePosition);
        }
    }

    private int getUpdatedRadius(int currentRadius){
        int radius = currentRadius;
        if(currentRadius != 0){
            radius--;
        }
        return radius;
    }

    private double getUpdatedLearningNorm(double currentLearningNorm, double learningNormTo, double learningNormDecrementStepValue){
        double learningNorm = currentLearningNorm;
        if(currentLearningNorm >= learningNormTo){
            learningNorm = currentLearningNorm - learningNormDecrementStepValue;
        }
        return learningNorm;
    }

    private OptimalClusterUpdatePosition getOptimalClusterUpdatePosition(int updateRadius, int clusterNeuronsCount){
        OptimalClusterUpdatePosition optimalClusterUpdatePosition = new OptimalClusterUpdatePosition(updateRadius, (clusterNeuronsCount - 1) - updateRadius);
        return optimalClusterUpdatePosition;
    }

    private int getClusterWinnerIndex(int clusterNeuronCount, ClusterNeuron[] clusters){
        double[] euclideanDistancesArray = new double[clusterNeuronCount];
        double minimalEuclideanDistance = (double)Integer.MAX_VALUE;
        int clusterWinnerIndex = 0;
        for(int i = 0; i < euclideanDistancesArray.length; ++i){
            euclideanDistancesArray[i] = clusters[i].getOutputSignal();
            if(euclideanDistancesArray[i] < minimalEuclideanDistance){
                minimalEuclideanDistance = euclideanDistancesArray[i];
                clusterWinnerIndex = i;
            }
        }
        return clusterWinnerIndex;
    }

    private void updateClustersInputEdgesWeight(int indexClusterToUpdate, double learningNorm, double[] currentInputSignal){
        Edge[] edgesToUpdate = this.inputEdges.get(indexClusterToUpdate);
        for(int i = 0; i < edgesToUpdate.length; ++i){
            Edge edge = edgesToUpdate[i];
            double signal = currentInputSignal[i];
            double updatedWeight = edge.getWeight() + learningNorm * (signal - edge.getWeight());
            edge.setWeight(updatedWeight);
        }
    }

    // TODO: make this method more elegant !!!
    private void updateClustersNearWinnerInputEdgesWeight(double[] currentInputSignal, int updateRadius, double learningNorm, int clusterWinnerIndex, int clusterNeuronCount, OptimalClusterUpdatePosition optimalClusterUpdatePosition){
        for(int i = 1; i != updateRadius; ++i){

            int currentUpdateIndexIncrement = clusterWinnerIndex + i;
            int currentUpdateIndexDecrement = clusterWinnerIndex - i;

            if(optimalClusterUpdatePosition.isPositionOptimal(currentUpdateIndexIncrement)){
                updateClustersInputEdgesWeight(currentUpdateIndexIncrement, learningNorm, currentInputSignal);
            } else {
                if(currentUpdateIndexIncrement < clusterNeuronCount){
                    updateClustersInputEdgesWeight(currentUpdateIndexIncrement, learningNorm, currentInputSignal);
                }
            }

            if(optimalClusterUpdatePosition.isPositionOptimal(currentUpdateIndexDecrement)){
                updateClustersInputEdgesWeight(currentUpdateIndexDecrement, learningNorm, currentInputSignal);
            } else {
                if(currentUpdateIndexDecrement >= 0){
                    updateClustersInputEdgesWeight(currentUpdateIndexDecrement, learningNorm, currentInputSignal);
                }
            }
        }
    }

    private List<double[]> getCurrentInputEdgesWeightList(List<Edge[]> inputEdgesList){
        List<double[]> inputEdgesWeightList = new ArrayList<double[]>(inputEdgesList.size());
        Iterator<Edge[]> inputEdgesIterator = inputEdgesList.iterator();

        while(inputEdgesIterator.hasNext()){
            Edge[] edges = inputEdgesIterator.next();
            double[] weight = new double[edges.length];

            for(int i = 0; i < edges.length; ++i){
                weight[i] = edges[i].getWeight();
            }
            inputEdgesWeightList.add(weight);
        }
        return inputEdgesWeightList;
    }

    private void saveNewInputEdgesWeight(List<double[]> inputEdgesWeightlist){
        try {
            StorageUtils.writeInputEdgesWeightToFile(this.context, inputEdgesWeightlist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<double[]> getRestoredInputEdgesWeightList(){
        return StorageUtils.getInputEdgesWeightFromFile();
    }

    private boolean isLearned(Context context, int currentNeuronClusterCount, int currentInputEdgesCount){
        // TODO: finish this method
        // TODO: check if file with data exists -> check if number of line is equals to number of clusters -> check if number of item in line is equals to input number

        if(StorageUtils.isFileExists(context)){
            if(StorageUtils.isRememberedClusterCountCorrect(currentNeuronClusterCount) && StorageUtils.isRememberedInputCountCorrect(currentInputEdgesCount)){
               return true;
            }
        }
        return false;
    }

// #MARK - Learning Additional class

    private class OptimalClusterUpdatePosition {

    // #MARK - Properties
        private int start;
        private int end;

    // #MARK - Constructors

        OptimalClusterUpdatePosition(){
            this.start = 0;
            this.end = 0;
        }

        OptimalClusterUpdatePosition(int start, int end){
            this.start = start;
            this.end = end;
        }

    // MARK - Custom Methods

        public boolean isPositionOptimal(int clusterPosition){
            if(clusterPosition >= this.start && clusterPosition <= this.end){
                return true;
            }
            return false;
        }

    }

}
