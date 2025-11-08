package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Card:");
        String term = sc.nextLine();
        System.out.println(term);

        System.out.println("Definition:");
        String definition = sc.nextLine();
        System.out.println(definition);
    }
}
