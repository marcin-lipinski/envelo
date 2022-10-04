package zadanie;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;
        boolean doContinue = true;

        KanyeQuotesClient yeClient = new KanyeQuotesClient();

        if(yeClient.build()){
            System.out.println("Type 'next' to get new quote or 'quit' to exit.");
            while(doContinue){
                command = scanner.next();
                if(command.equals("next")){
                    doContinue = yeClient.askForQuote();
                }
                if(command.equals("quit")) return;
            }
        }
    }
}


