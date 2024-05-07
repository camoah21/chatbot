package main.java.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

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
        try (Scanner scanner = new Scanner(System.in); PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter("chatbot.txt", true)))) {
            System.out.println("Hello, I'm a chatbot. How can I help you today?");
            String userInput, response;

            while (true) {
                System.out.print("You: ");
                userInput = scanner.nextLine();
                if ("bye".equalsIgnoreCase(userInput)) {
                    break;
                }
                p.printf("You: %s\n", userInput);

                System.out.print("Chatbot: ");
                response = scanner.nextLine();
                p.printf("Chatbot: %s\n", response);
            }
            System.out.println("Chat session ended.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
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
    public static List<String> Preprocessing() throws IOException {
        List<String> tokens = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("chatbot.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Remove punctuation and convert to lower case
                line = line.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase();
                // Tokenize the cleaned line
                StringTokenizer tokenizer = new StringTokenizer(line);
                while (tokenizer.hasMoreTokens()) {
                    tokens.add(tokenizer.nextToken());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            throw e;
        }
        return tokens;
    }
}
