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
        this.signal = 0;
        this.weight = 0;
    }

    Edge(double weight){
        this.signal = 0;
        this.weight = weight;
    }

    Edge(double weight, double signal){
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
