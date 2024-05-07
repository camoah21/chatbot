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

    /**
     * The SimpleNeuralNetwork constructor initializes the weights and biases of
     * the neural network.
     *
     * @param numInput The number of input nodes
     * @param numHiddenNodes The number of hidden nodes
     * @param numClasses The number of output classes
     * @param seed The seed for the random number generator
     * @return The number of output classes
     *
     * @docauthor Chris Amoah
     */
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

    /**
     * The initializeWeights function initializes the weights and biases of the
     * neural network. The weights are initialized using Xavier initialization,
     * which is a method to initialize the weights such that they have a
     * variance of 2/(numInput + numHiddenNodes) for layer 1, and
     * 2/(numHiddenNodes + numClasses) for layer 2. This ensures that each
     * neuron in each layer has an equal chance to be activated.
     *
     *
     * @return Nothing
     *
     * @docauthor Chris Amoah
     */
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

    /**
     * The forwardPass function performs a forward pass through the neural
     * network. It takes an input array, applies the weights and biases to it,
     * and returns the output of the neural network.
     *
     * @param input The input array to the neural network
     * @return The output of the neural network
     *
     * @docauthor Chris Amoah
     */
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

    /**
     * The relu function takes a double as input and returns the maximum of 0.0
     * or the input value.
     *
     *
     * @param double x Represent the input value
     *
     * @return The maximum of 0 and x
     *
     * @docauthor Chris Amoah
     */
    private double relu(double x) {
        return Math.max(0.0, x);
    }

    /**
     * The softmax function takes an array of doubles as input and returns an
     * array of doubles with the same length as the input array. The softmax
     * function is used to normalize the output of the neural network, ensuring
     * that the sum of the outputs is 1.
     *
     * @param double[] x Represent the input array
     *
     * @return The normalized array
     *
     * @docauthor Chris Amoah
     */
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

    /**
     * The save function takes a filename as input and saves the entire object
     * to the file.
     *
     * @param String filename Represent the filename
     *
     * @return Nothing
     *
     * @docauthor Chris Amoah
     */
    public void save(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this); // Write the entire object to the file
            System.out.println("Model saved successfully to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving model: " + e.getMessage());
        }
    }

}
