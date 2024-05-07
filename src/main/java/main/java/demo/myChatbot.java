package main.java.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.deeplearning4j.datasets.iterator.impl.IrisDataSetIterator;

public class myChatbot {

    /**
     * The DataCollector function collects data from the user and writes it to a
     * file. The function prompts the user for input, then writes their response
     * to a file. This continues until the user says &quot;bye&quot;.
     *
     *
     * @return A void, because it is not returning any values
     *
     * @docauthor Chris Amoah
     */
    public static void DataCollector() {
        try (PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter("chatbot.txt", true)))) {
            // Chatbot greets user
            p.printf("Hello, I'm a chatbot. How can I help you today?\n");

            // Collect conversation data until user says "bye"
            try ( // User inputs
                    Scanner scanner = new Scanner(System.in)) {
                // Collect conversation data until user says "bye"
                String userInput;
                do {
                    // Read user input
                    System.out.print("You: ");
                    userInput = scanner.nextLine();

                    // Write user input to file
                    p.printf("You: %s\n", userInput);

                    // Prompt for response
                    System.out.print("Chatbot: ");
                    String response = scanner.nextLine();

                    // Write response to file
                    p.printf("Chatbot: %s\n", response);
                } while (!userInput.equalsIgnoreCase("bye"));
                System.out.println("Data collected successfully!");
                // Close resources
            }
            p.close();
            System.out.println("Resources closed.");
            System.out.println("Data collected successfully!");
        } catch (IOException e) {
            // Let user know if error occurs
            System.out.println("An error occurred.");
        }
    }

    // Preprocessing
    /**
     * The Preprocessing function reads the chatbot.txt file and tokenizes it
     * into a list of strings, removing all punctuation and converting all
     * characters to lowercase.
     *
     * @return null
     *
     *
     *
     *
     * @docauthor Chris Amoah
     */
    public static IrisDataSetIterator Preprocessing() throws IOException {
        // TokenizerFactory
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/chrisofori/Downloads/chatbot.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        String text = contentBuilder.toString();
        System.out.println(text);
        text = text.replaceAll("[^a-zA-Z0-9 ]", "");
        text = text.toLowerCase();
        StringTokenizer tokenizer = new StringTokenizer(text);
        List<String> tokens = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }

        // Vectorization
        List<String> vector = new ArrayList<>();
        for (String token : tokens) {
            vector.add(token);
        }
        int[] bow = new int[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            bow[i] = vector.get(i).hashCode();
            tokens.get(bow[i]);
        }

        // Print the vector
        System.out.println(Arrays.toString(bow));
        return null
    }
}
