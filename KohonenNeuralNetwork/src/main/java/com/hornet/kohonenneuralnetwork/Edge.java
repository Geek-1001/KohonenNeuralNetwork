package com.hornet.kohonenneuralnetwork;

/**
 * Created by Ahmed on 5/17/14.
 */
public class Edge {

// #MARK - Constants

    private double signal;
    private double weight;

// #MARK - Constructors

    Edge(){
        init(0, 0);
    }

    Edge(double weight){
        init(weight, 0);
    }

    Edge(double weight, double signal){
        init(weight, signal);
    }

    private void init(double weight, double signal){
        this.signal = signal;
        this.weight = weight;
    }

// #MARK - Custom Methods

    public void setSignal(double signal){
        this.signal = signal;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public double getWeight(){
        return this.weight;
    }

    public double getSignal(){
        return this.signal;
    }

}
