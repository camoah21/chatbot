package main.java.demo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

public class SimpleNeuralNetwork {

    private int numInput;
    private int numHiddenNodes;
    private int numClasses;
    private Random rand;
    private double[][] weights1;
    private double[] biases1;
    private double[][] weights2;
    private double[] biases2;

    public int SimpleNeuralNetwork(int numInput, int numHiddenNodes, int numClasses, long seed) {
        this.numInput = numInput;
        this.numHiddenNodes = numHiddenNodes;
        this.numClasses = numClasses;
        this.rand = new Random(seed);

        // Initialize weights and biases
        double[][] weights1 = new double[numHiddenNodes][numInput];
        double[][] weights2 = new double[numClasses][numHiddenNodes];
        double[] biases1 = new double[numHiddenNodes];
        double[] biases2 = new double[numClasses];

        initializeWeights();
        return numClasses;
    }

    private void initializeWeights() {
        // Xavier initialization for weights
        double std1 = Math.sqrt(2.0 / (numInput + numHiddenNodes));
        double std2 = Math.sqrt(2.0 / (numHiddenNodes + numClasses));

        for (int i = 0; i < numHiddenNodes; i++) {
            for (int j = 0; j < numInput; j++) {
                weights1[i][j] = rand.nextGaussian() * std1;
            }
            biases1[i] = 0.0;
        }

        for (int i = 0; i < numClasses; i++) {
            for (int j = 0; j < numHiddenNodes; j++) {
                weights2[i][j] = rand.nextGaussian() * std2;
            }
            biases2[i] = 0.0;
        }
    }

    public double[] forwardPass(double[] input) {
        // First layer
        double[] hiddenLayer = new double[numHiddenNodes];
        for (int i = 0; i < numHiddenNodes; i++) {
            for (int j = 0; j < numInput; j++) {
                hiddenLayer[i] += input[j] * weights1[i][j];
            }
            hiddenLayer[i] += biases1[i];
            hiddenLayer[i] = relu(hiddenLayer[i]);
        }

        // Output layer
        double[] outputLayer = new double[numClasses];
        for (int i = 0; i < numClasses; i++) {
            for (int j = 0; j < numHiddenNodes; j++) {
                outputLayer[i] += hiddenLayer[j] * weights2[i][j];
            }
            outputLayer[i] += biases2[i];
        }
        return softmax(outputLayer);
    }

    private double relu(double x) {
        return Math.max(0.0, x);
    }

    private double[] softmax(double[] x) {
        double max = Double.NEGATIVE_INFINITY;
        for (double value : x) {
            if (value > max) {
                max = value;
            }
        }

        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            x[i] = Math.exp(x[i] - max); // stability improvement by subtracting max
            sum += x[i];
        }

        for (int i = 0; i < x.length; i++) {
            x[i] /= sum;
        }

        return x;
    }

    public void save(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this); // Write the entire object to the file
            System.out.println("Model saved successfully to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving model: " + e.getMessage());
        }
    }

}
