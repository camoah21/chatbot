package main.java.demo;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.datasets.iterator.impl.IrisDataSetIterator;
import org.deeplearning4j.eval.Evaluation;

public class ModelUtils {
    public static MultiLayerNetwork createSimpleModel(IrisDataSetIterator irisIter) {
        int numInput = 4; // Number of input features (iris dataset has 4 features)
        int numClasses = 3; // Number of output classes (iris dataset has 3 classes)

        int seed = 123; // Seed for reproducibility
        int numHiddenNodes = 10; // Number of nodes in the hidden layer

        // Build the neural network configuration
        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
            .seed(seed)
            .weightInit(WeightInit.XAVIER)
            .updater(org.nd4j.linalg.learning.config.Adam.builder().learningRate(0.001).build())
            .list()
            .layer(new DenseLayer.Builder()
                    .nIn(numInput)
                    .nOut(numHiddenNodes)
                    .activation(Activation.RELU)
                    .build())
            .layer(new DenseLayer.Builder()
                    .nIn(numHiddenNodes)
                    .nOut(numClasses)
                    .activation(Activation.SOFTMAX)
                    .build())
            .build();

        // Create and initialize the MultiLayerNetwork
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();

        // Train the model (optional)
        int numEpochs = 10;
        model.setListeners(new ScoreIterationListener(10)); // Print score every 10 iterations
        for (int i = 0; i < numEpochs; i++) {
            irisIter.reset(); // Reset iterator before each epoch
            model.fit(irisIter); // Fit the model on the dataset
        }

        // Evaluate the model (optional)
        irisIter.reset(); // Reset iterator before evaluation
        Evaluation eval = model.evaluate(irisIter);
        System.out.println(eval.stats());

        return model;
    }
}
