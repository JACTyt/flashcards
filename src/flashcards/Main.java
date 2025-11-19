package flashcards;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the number of cards: ");
        int n = Integer.parseInt(sc.nextLine());

        String term = "", definition = "";
        String[] answers = new String[n];
        Map<String, String> dictionary = new LinkedHashMap<String, String>();

        int i = 0;
        while (i < n) {

            System.out.println("Card #" + (i + 1) + ": ");

            while (true) {
                term = sc.nextLine();
                if (dictionary.containsKey(term)) {
                    System.out.println("The term \"" + term + "\" already exists. Try again:");
                } else {
                    break;
                }
            }

            System.out.println("The definition for card #" + (i + 1) + ": ");
            while (true) {
                definition = sc.nextLine();
                if (dictionary.containsValue(definition)) {
                    System.out.println("The term \"" + definition + "\" already exists. Try again:");
                } else {
                    break;
                }
            }

            dictionary.put(term, definition);
            i++;
        }
        String[] terms = dictionary.keySet().toArray(new String[0]);
        String[] definitions = dictionary.values().toArray(new String[0]);
        for (i = 0; i < n; i++) {
            String termToAsk = terms[i];
            System.out.println("Print the definition of \"" + termToAsk + "\":");
            answers[i] = sc.nextLine();
            if (dictionary.containsValue(answers[i])) {
                if (answers[i].equals(definitions[i])) {
                    System.out.println("Correct!");
                } else {
                    for (int j = 0; j < definitions.length; j++) {
                        if (answers[i].equals(definitions[j]) && i != j) {
                            System.out.println("Wrong. The right answer is \"" + definitions[i] + "\", but your definition is correct for \"" + terms[j] + "\".");
                            break;
                        }
                    }
                }
            } else {
                System.out.println("Wrong. The right answer is \"" + definitions[i] + "\".");
            }
        }
    }
}
