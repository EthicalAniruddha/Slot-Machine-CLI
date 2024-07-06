/** A mini game program in console, I hope you will understand my code (I don't know any advance stuff) */

//Necessay imports
import java.util.Scanner;
import java.util.Random;

public class SlotMachine {

    // Symbols used
    public static String sym0 = "!";
    public static String sym1 = "@";
    public static String sym2 = "#";
    public static String sym3 = "&";
    public static String sym4 = "%";

    // Strings for decoration in terminal
    public static String space = "-------------------------------------------------------------------------------------------------------------";
    public static String gap = "        ";

    public static String symbols[] = new String[5]; // To store symbols
    public static String result[] = new String[3]; // To store randomly picked symbols
    public static String names[]; // To store name of players
    public static int storeCurrentBalance[]; // To store the balance of the player after bet
    public static int storeGain[]; // To store the total gain of the player at end of game

    public static int playerNum = 0; // No. of players

    public static int playerBalance = 0; // Current player balance
    public static int bet = 0; // Player's bet
    public static int gain = 0; // Winning bet
    public static int totalGain = 0; // To store total winning bet


    // Help menu
    public static void helpMenu() {

        System.out.print(space+"\n");
        System.out.print(gap+"Help menu: \n");
        System.out.println(gap+"Game rules ~ There are 5 symbols each having its own value.\n");
        System.out.println(gap+gap+">> When a player gets jackpot");
        System.out.print(gap+gap+sym0+" > gives 2x bet in return.\n");
        System.out.print(gap+gap+sym1+" > gives 3x bet in return.\n");
        System.out.print(gap+gap+sym2+" > gives 6x bet in return.\n");
        System.out.print(gap+gap+sym3+" > gives 10x bet in return.\n");
        System.out.println(gap+gap+sym4+" > gives 20x bet in return.\n");
        System.out.println(gap+gap+">> When player gets normal pot");
        System.out.print(gap+gap+sym0+" > gives +5 in return.\n");
        System.out.print(gap+gap+sym1+" > gives +6 in return.\n");
        System.out.print(gap+gap+sym2+" > gives +8 in return.\n");
        System.out.print(gap+gap+sym3+" > gives +10 in return.\n");
        System.out.println(gap+gap+sym4+" > gives +15 in return.\n");
        System.out.print(gap+gap+"NOTE > A player will win if current balance of the player will be greater than other players.\n");
        System.out.print(gap+gap+"NOTE > If the bet is equal to current balance of the player then they quit the game.\n");
        System.out.println(gap+gap+"        But if the player wins any pot then the gain will be counted.\n");
	    System.out.print(gap+"Now enjoy game.\n");
        System.out.println(space);
    }

    // Generate symbols
    public static void generateSymbols() {
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result[i] = symbols[random.nextInt(0,5)];
        }
    }

    // Print symbol on screen
    public static void printGeneratedSymbols() {
        System.out.println("\n"+gap+gap+"-------------");
        System.out.print(gap+gap+"| "+result[0]+" | "+result[1]+" | "+result[2]+" |\n");
        System.out.println(gap+gap+"-------------\n");
    }

    // To switch pots. If player wins a normal pot or jackpot
    public static void switchPotNorJack(int index, char ch) {

        if (ch == 'J') {
            switch (result[index]) {

                case "!":
                    gain = bet*2;
                    break;
    
                case "@":
                    gain = bet*3;
                    break;
    
                case "#":
                    gain = bet*6;
                    break;
    
                case "&":
                    gain = bet*10;
                    break;
    
                case "%":
                    gain = bet*20;
                    break;
            }
        } else if (ch == 'N') {

            switch (result[index]) {

                case "!":
                    gain = 5;
                    break;
    
                case "@":
                    gain = 6;
                    break;
    
                case "#":
                    gain = 8;
                    break;
    
                case "&":
                    gain = 10;
                    break;
    
                case "%":
                    gain = 15;
                    break;
            }
        }
    }

    // To check if the player has won jackpot or not
    public static boolean checkJackPot() {

        if (result[0] == result[1] && result[1] == result[2]) {

            switchPotNorJack(0, 'J');
            return true;
        }
        return false;
    }

    // To check normal pot, if any two symbols matches then we will return some money
    public static boolean checkNormalPot() {
        if (result[0] == result[1] || result[0] == result[2]) {
            switchPotNorJack(0, 'N');
            return true;
        } else if (result[1] == result[2] || result[1] == result[0]) {
            switchPotNorJack(1, 'N');
            return true;
        } else if (result[2] == result[0] || result[2] == result[1]){
            switchPotNorJack(2, 'N');
            return true;
        }
        return false;
    }

    // Function to play the game
    public static void playGame(Scanner sc) {

        // Initialising the arrays
        names = new String[playerNum];
        storeCurrentBalance = new int[playerNum];
        storeGain = new int[playerNum];

        // Taking players name
        for (int i = 0; i < playerNum; i++) {
            System.out.print("\n"+gap+"Enter player"+(i+1)+"'s name: ");
            names[i] = sc.next();
        }

        // Main area for running the game
        for(int i = 0; i < playerNum; i++) {

            playerBalance = 100; totalGain = 0;

            System.out.print("\n"+gap+names[i]+"'s turn! Your balance is rs."+playerBalance+", spend wisely.");
            String choice = "Y";

            do {

                System.out.print("\n"+gap+"Enter your bet: ");
                String check = sc.next();

                try { // To prevent the program from crashing if the input is other than integer or is an invalid input
                    bet = Integer.parseInt(check);
                    if (bet > playerBalance) {
                      System.out.print("\n"+gap+"Amount cannot be taken as it exceeds your limit....\n");  
                        continue;
                    } else if (bet < 0) {
                        System.out.print("\n"+gap+"Amount cannot be entered negative....\n");
                        continue;
                    } else if (bet == 0) {
                        System.out.print("\n"+gap+"Seriously??....\n");
                        continue;
                    }
                     
                } catch (NumberFormatException e) {
                    System.out.print("\n"+gap+"Enter a decimal number.");
                    continue;
                }

                generateSymbols(); // To generate of symbols

                int checkBalance = playerBalance - bet;

                // To check if the final balance is valid or not 
                if (checkBalance < 0) {
                    System.out.print("\n"+gap+"You are broke. Re-enter your bet. \n");
                    continue;
                } else if (checkBalance == 0) {
                    System.out.print("\n"+gap+"Since your current balance is zero let the developer quit for you.\n");
                    if (checkJackPot()) {
                        printGeneratedSymbols();
                        System.out.print(gap+"Atlease you have won a jackpot.. be happy");
                        playerBalance = gain;
                        totalGain += gain;
                    } else if(checkNormalPot()) {
                        printGeneratedSymbols();
                        System.out.print(gap+"You have won a normal pot!");
                        playerBalance = gain;
                        totalGain += gain;
                    } else {
                        printGeneratedSymbols();
                        playerBalance = 0;
                        System.out.print(gap+"You have lost.");
                    }
                    System.out.print(gap+"Your current balance rs."+(playerBalance));
                    choice = "N";
                } else {

                    playerBalance = checkBalance;
                        
                    printGeneratedSymbols(); // To print the generated symbols

                    if (checkJackPot()) {
                        System.out.print(gap+"You have won a jackpot!");
                        playerBalance += gain;
                        totalGain += gain;
                    } else if (checkNormalPot()){
                        System.out.print(gap+"You have won a normal pot!");
                        playerBalance += gain;
                        totalGain += gain;
                    } else {
                        System.out.print(gap+"You have lost.");
                    }

                    System.out.print(gap+"Your current balance rs."+(playerBalance));

                    while(true) {

                        System.out.print("\n"+gap+"Wanna continue? (y/n): ");
                        choice = sc.next();

                        if (choice.equals("Y") || choice.equals("y")) {
                            break;
                        } else if (choice.equals("/exit")) {
                            System.out.println("\n"+gap+"Uhh.. you didn't like my game?\n");
                            System.exit(0);
                        } else if (choice.equals("n") || choice.equals("N")) {
                            System.out.print("\n"+gap+names[i]+" you have lost your chance to win a jackpot.");
                            break;
                        } else {
                            System.out.print("\n"+gap+"Please enter a valid choice (y/n) 'y' to continue and 'n' to stop.");
                        }
                    }
                }

                if (choice.equals("n") || choice.equals("N")) {
                    break;
                }

            } while (choice.equals("Y") || choice.equals("y"));

            storeCurrentBalance[i] = playerBalance;
            storeGain[i] = totalGain;

            System.out.println();
        }

        // To show the score board
        System.out.print("\n"+space);
        for (int i = 0; i < playerNum; i++) {
            System.out.println("\n"+gap+names[i]+" has balance rs."+storeCurrentBalance[i]+" with a total gain of rs."+storeGain[i]);
        }
        System.out.print(space+"\n");
    }

    //Our main function
    public static void main(String[] args) {

        // Set symbols[]
        symbols[0] = sym0;
        symbols[1] = sym1;
        symbols[2] = sym2;
        symbols[3] = sym3;
        symbols[4] = sym4;

        playerBalance = 0; bet = 0; gain = 0; // Setting the initial values
        
        while(true) {
            // Creating scanner object sc
            Scanner sc = new Scanner(System.in);

            //Taking input from user
            System.out.print("\n"+gap+"Enter \"/help\" for help menu, \"/exit\" to quit game or \"/play\" to play game: ");
            String command = sc.next();

            System.out.println(); // For a new line feed

            // Checking the three cases
            switch (command) {
                // For help menu
                case "/help":
                    helpMenu();
                    break;

                // To exit the game
                case "/exit":
                    System.out.println(gap+"Done already? I hope you enjoyed it.\n");
                    sc.close();
                    System.exit(0);

                // To play the game
                case "/play":
                    do {
                        System.out.print(gap+"Enter number of players (2-4): ");
                        String num = sc.next();
                        try { // To prevent the program from crashing if the given input is in String or double
                            playerNum = Integer.parseInt(num);
                            if (playerNum >=2 && playerNum <= 4) {
                                playGame(sc);
                                break;
                            } else {
                                System.out.print("\n"+gap+"Enter number of player between (2-4).\n");
                            }
                        } catch(NumberFormatException e) {
                            System.out.print("\n"+gap+"Give a decimal input.\n");
                        }
                    } while (true);
                    break;
            
                // If the command is invalid then this loop will be continued
                default:
                    System.out.print(gap+gap+"Invalid command.\n");
            }
        }
    }
}
