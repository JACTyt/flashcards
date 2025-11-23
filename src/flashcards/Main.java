package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    static Map<String, String> dictionary = new TreeMap<String, String>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            Scanner sc = new Scanner(System.in);
            switch (sc.nextLine()) {
                case "add":
                    addCard();
                    break;
                case "remove":
                    removeCard();
                    break;
                case "import":
                    importFile();
                    break;
                case "export":
                    exportFile();
                    break;
                case "ask":
                    askCard();
                    break;
                case "exit":
                    System.out.println("Bye bye!");
                    return;
            }
        }
        /*
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

         */
    }

    private static void exportFile() {
        System.out.println("File name:");
        String fileName = sc.nextLine();

        int savedCount = 0;

        try (FileWriter writer = new FileWriter(fileName)) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue() + "\n");
                savedCount++;
            }
            System.out.println(savedCount + " cards have been saved.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the file.");
        }
    }

    private static void askCard() {
        System.out.println("How many times to ask?\n");
        int count = Integer.parseInt(sc.nextLine());

        Random random = new Random();
        List<String> terms = new ArrayList<>(dictionary.keySet());
        for (int i = 0; i < count; i++) {
            String term = terms.get(random.nextInt(terms.size()));
            String definition = dictionary.get(term);

            System.out.println("Print the definition of \"" + term + "\":");
            String answer = sc.nextLine();

            if (answer.equals(definition)) {
                System.out.println("Correct!");
            } else if (dictionary.containsValue(answer)) {
                String otherTerm = dictionary.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(answer))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse("");
                System.out.println("Wrong. The right answer is \"" + definition +
                        "\", but your answer is correct for \"" + otherTerm + "\".");
            } else {
                System.out.println("Wrong. The right answer is \"" + definition + "\".");
            }
        }
    }

    private static void importFile() {
        System.out.println("File name:");
        String inputFile = sc.nextLine();

        File file = new File(inputFile);
        if (!file.exists()) {
            System.out.println("File not found.");
            return;
        }

        int loadedCount = 0;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String term = parts[0];
                    String definition = parts[1];
                    dictionary.put(term, definition);
                    loadedCount++;
                }
            }
            System.out.println(loadedCount + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public static void addCard() {
        System.out.println("The card:");
        String term = sc.nextLine();

        if (dictionary.containsKey(term)) {
            System.out.println("The card \"" + term + "\" already exists.");
            return;
        }

        System.out.println("The definition of the card:");
        String definition = sc.nextLine();

        if (dictionary.containsValue(definition)) {
            System.out.println("The definition \"" + definition + "\" already exists.");
            return;
        }

        System.out.println("The pair (\"" + term + "\":\"" + definition + "\") has been added");
        dictionary.put(term, definition);
        System.out.println();
    }

    public static void removeCard() {
        System.out.println("Which card?");
        String term = sc.nextLine();

        if (!dictionary.containsKey(term)) {
            System.out.println("Can't remove \"" + term + "\": there is no such card.");
            return;
        }

        dictionary.remove(term);
        System.out.println("The card has been removed.");
    }
}
