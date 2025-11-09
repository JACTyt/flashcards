package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the number of cards: ");
        int n = Integer.parseInt(sc.nextLine());

        String[] terms = new String[n];
        String[] definitions = new String[n];
        String[] answers = new String[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Card #" + (i+1) + ": ");
            terms[i] = sc.nextLine();
            System.out.println("The definition for card #"+(i+1)+": ");
            definitions[i] = sc.nextLine();
        }
        for (int i = 0; i < n; i++) {
            System.out.println("Print the definition of \"" + terms[i] + "\":");
            answers[i] = sc.nextLine();
            if(definitions[i].equals(answers[i])){
                System.out.println("Correct!");
            }
            else{
                System.out.println("Wrong. The right answer is \"" + definitions[i] + "\".");
            }
        }
    }
}
