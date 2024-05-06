
// Import necessary libraries
import java.io.File;
import java.io.IOException;

import org.deeplearning4j.datasets.iterator.impl.IrisDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import main.java.demo.ModelUtils;
import main.java.demo.myChatbot.*;

public class ChatbotTrainer {

    /**
     * The main function is used to train a neural network model. The input and
     * output sizes are defined, the dataset is loaded and preprocessed, a
     * MultiLayerNetwork model is created, trained on the dataset using an
     * iterator, and finally saved as &quot;trained_model.zip&quot;.
     *
     *
     * @param String[] args Pass command line arguments to the program
     *
     * @return The trained model
     * @throws IOException
     *
     * @docauthor Chris Amoah
     */
    public static void main(String[] args) throws IOException {
        // Load and preprocess the dataset
        IrisDataSetIterator iterator = myChatbot.Preprocessing();

        // Create a model
        MultiLayerNetwork model = ModelUtils.createSimpleModel(iterator);

        // Train the model
        int numEpochs = 10; // Number of training epochs
        for (int i = 0; i < numEpochs; i++) {
            model.fit(iterator); // Fit the model on the dataset
        }

        // Save the trained model
        File modelFile = new File("trained_model.zip");
        model.save(modelFile);
        System.out.println("Trained model saved to: " + modelFile.getAbsolutePath());
    }
}
