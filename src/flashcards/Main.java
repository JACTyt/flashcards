package flashcards;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String term = sc.nextLine();
        System.out.println(term);

        String definition = sc.nextLine();
        System.out.println(definition);

        String answer = sc.nextLine();
        if(definition.equals(answer)) {
            System.out.println("Your answer is right!");
        }
        else{
            System.out.println("Your answer is wrong...");
        }
    }
}
