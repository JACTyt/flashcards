package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    static Map<String, String> dictionary = new TreeMap<String, String>();
    static Map<String, Integer> wrongCardsGuesses = new TreeMap<>();
    private static List<String> log = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            logOutput("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
            Scanner sc = new Scanner(System.in);
            switch (logInput()) {
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
                case "log":
                    logs();
                    break;
                case "hardest card":
                    hardestCard();
                    break;
                case "reset stats":
                    resetStats();
                    break;
                case "exit":
                    logOutput("Bye bye!");
                    return;
            }
        }
    }

    private static void hardestCard() {
        if (wrongCardsGuesses.isEmpty()) {
            logOutput("There are no cards with errors.\n");
            return;
        }

        int max = Collections.max(wrongCardsGuesses.values());

        List<String> hardest = new ArrayList<>();
        for (var entry : wrongCardsGuesses.entrySet()) {
            if (entry.getValue() == max) {
                hardest.add(entry.getKey());
            }
        }

        if (hardest.size() == 1) {
            String term = hardest.get(0);
            logOutput("The hardest card is \"" + term + "\". You have " + max + " errors answering it.");
        } else {
            String joined = String.join("\", \"", hardest);
            logOutput("The hardest cards are \"" + joined + "\". You have " + max + " errors answering them.");
        }
    }

    private static void resetStats() {
        wrongCardsGuesses.clear();
        logOutput("Card statistics have been reset.");
    }

    private static void logs() {
        logOutput("File name:");
        String fileName = logInput();
        try (FileWriter writer = new FileWriter(fileName)) {
            for (String entry : log) {
                writer.write(entry + System.lineSeparator());
            }
            logOutput("The log has been saved.");
        } catch (IOException e) {
            logOutput("An error occurred while saving the file.");
        }

    }

    private static void exportFile() {
        logOutput("File name:");
        String fileName = logInput();

        int savedCount = 0;

        try (FileWriter writer = new FileWriter(fileName)) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                int mistakes = wrongCardsGuesses.get(entry.getKey()) == null ? 0
                        : wrongCardsGuesses.get(entry.getKey());
                writer.write(entry.getKey() + ":" + entry.getValue() + ":" + mistakes + "\n");
                savedCount++;
            }
            logOutput(savedCount + " cards have been saved.");
        } catch (IOException e) {
            logOutput("An error occurred while saving the file.");
        }
    }

    private static void askCard() {
        logOutput("How many times to ask?\n");
        int count = Integer.parseInt(logInput());

        Random random = new Random();
        List<String> terms = new ArrayList<>(dictionary.keySet());
        for (int i = 0; i < count; i++) {
            String term = terms.get(random.nextInt(terms.size()));
            String definition = dictionary.get(term);

            logOutput("Print the definition of \"" + term + "\":");
            String answer = logInput();

            if (answer.equals(definition)) {
                logOutput("Correct!");
            } else if (dictionary.containsValue(answer)) {
                int gets = wrongCardsGuesses.get(term) == null ? 0 : wrongCardsGuesses.get(term);
                wrongCardsGuesses.put(term, gets + 1);
                String otherTerm = dictionary.entrySet().stream()
                        .filter(entry -> entry.getValue().equals(answer))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse("");
                logOutput("Wrong. The right answer is \"" + definition +
                        "\", but your answer is correct for \"" + otherTerm + "\".");
            } else {
                int gets = wrongCardsGuesses.get(term) == null ? 0 : wrongCardsGuesses.get(term);
                wrongCardsGuesses.put(term, gets + 1);
                logOutput("Wrong. The right answer is \"" + definition + "\".");
            }
        }
    }

    private static void importFile() {
        logOutput("File name:");
        String inputFile = logInput();

        File file = new File(inputFile);
        if (!file.exists()) {
            logOutput("File not found.");
            return;
        }

        int loadedCount = 0;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(":");
                if (parts.length == 3) {
                    String term = parts[0];
                    String definition = parts[1];
                    int mistakes = Integer.parseInt(parts[2]);
                    wrongCardsGuesses.put(term, mistakes);
                    dictionary.put(term, definition);
                    loadedCount++;
                } else if (parts.length == 2) {
                    String term = parts[0];
                    String definition = parts[1];
                    int mistakes = 0;
                    wrongCardsGuesses.put(term, mistakes);
                    dictionary.put(term, definition);
                    loadedCount++;
                }
                //System.out.println(line + " " + loadedCount);
            }
            logOutput(loadedCount + " cards have been loaded.");
        } catch (FileNotFoundException e) {
            logOutput("File not found.");
        }
    }

    public static void addCard() {
        logOutput("The card:");

        String term = logInput();

        if (dictionary.containsKey(term)) {
            logOutput("The card \"" + term + "\" already exists.");
            return;
        }

        logOutput("The definition of the card:");
        String definition = logInput();

        if (dictionary.containsValue(definition)) {
            logOutput("The definition \"" + definition + "\" already exists.");
            return;
        }

        logOutput("The pair (\"" + term + "\":\"" + definition + "\") has been added");
        dictionary.put(term, definition);
    }

    public static void removeCard() {
        logOutput("Which card?");
        String term = logInput();

        if (!dictionary.containsKey(term)) {
            logOutput("Can't remove \"" + term + "\": there is no such card.");
            return;
        }

        dictionary.remove(term);
        logOutput("The card has been removed.");
    }

    private static void logOutput(String s) {
        System.out.println(s);
        log.add(s);
    }

    private static String logInput() {
        String s = sc.nextLine();
        log.add(s);
        return s;
    }
}
