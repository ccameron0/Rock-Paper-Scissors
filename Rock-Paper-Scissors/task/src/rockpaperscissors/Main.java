package rockpaperscissors;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {
    static int currentRating = 0;
    static String userName;

    static String[] createOptions() {
        Scanner scan = new Scanner (System.in);
        String options = scan.nextLine();
        if (options.isEmpty()) {
            options = "rock,paper,scissors";
        }
        //System.out.println(options);
        String[] optionsArray = options.split(",");
        return optionsArray;

    }

    static void playGame (String[] optionsArray) {

        System.out.println("Okay, let's start");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();


        Random random = new Random();
        int computerChoice;

        String[] auxiliaryArray = new String[(2 * optionsArray.length)];

        // Copy a[] to b[] two times
        for (int i = 0; i < optionsArray.length; i++) {
            auxiliaryArray[i] = optionsArray[i];
            auxiliaryArray[i+ optionsArray.length] = optionsArray[i];
        }

        while(true) {


            computerChoice = random.nextInt(3);
            String compChoiceString = optionsArray[computerChoice];

            /**System.out.println(optionsArray[0]);

            System.out.println(computerChoice);
            System.out.println(compChoiceString);
            System.out.println(input);**/

            boolean playerWon = false;
            boolean draw = false;
            boolean invalidInput = false;

            if(Objects.equals(input, "!exit")) {
                break;
            }
            else if(Objects.equals(input,"!rating")) {
                System.out.println(currentRating);

            }
            else if (!Arrays.asList(optionsArray).contains(input)) {
                System.out.println("Invalid input");
                invalidInput = true;
            }
            else if(compChoiceString.equals(input)) {
                System.out.println("There is a draw (" + input + ")" );
                draw = true;
                addPoints(50);
            }
            else {
                for(int i = computerChoice; i <= computerChoice+ optionsArray.length/2; i++) {
                    if(Objects.equals(input,auxiliaryArray[i])) {
                        System.out.println("Well done. The computer chose " +
                                compChoiceString + " and failed");
                        playerWon = true;
                        addPoints(100);
                        break;
                    }
                }
            }
            if(!playerWon && !draw && !input.equals("!rating") && !invalidInput) {
                System.out.println("Sorry, but the computer chose " + compChoiceString);
            }
            input = scan.nextLine();
        }
        System.out.println("Bye!");
    }

    static void addPoints (int points) {
        currentRating += points;
    }

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.print("Enter your name: ");
        userName = scan.next();
        System.out.println("Hello, " + userName);

        File file = new File("rating.txt");
        currentRating = 0;

        try (Scanner scanFile = new Scanner(file)) {

            while (scanFile.hasNextLine()) {
                String readString = scanFile.next();
                if(readString.equals(userName)) {
                    currentRating = scanFile.nextInt();
                    break;
                }
            }
        } catch(FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        playGame(createOptions());



    }
}
