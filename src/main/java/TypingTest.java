import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class TypingTest {

    private static final Scanner scanner = new Scanner(System.in);

    public static int testWord(String wordToTest) {
        try {
            int result = 0 ;
            System.out.println(wordToTest);
            String lastInput;

            lastInput =scanner.nextLine();

            System.out.println();
            System.out.println("You typed: " + lastInput);
            if (lastInput.equals(wordToTest)) {
                System.out.println("Correct");
                result++;
            } else {
                System.out.println("Incorrect");
            }
            return result ;
        } catch (Exception e) {
            e.printStackTrace();
            return -1 ;
        }
    }

    public static void typingTest(List<String> inputList) throws InterruptedException {
        int correctNum = 0 ;
        for (String wordToTest : inputList) {
            correctNum += testWord(wordToTest);
            Thread.sleep(2000); // Pause briefly before showing the next word
        }

        System.out.println("You answered " + correctNum + " test correctly and " + (10-correctNum) +" test incorrectly.");
    }

    public static int[] generateRandomNumbers(int count, int min, int max) {
        if (count <= 0 || min >= max) {
            throw new IllegalArgumentException("The input is invalid.");
        }

        Random random = new Random();
        int[] numbers = new int[count];

        for (int i = 0; i < count; i++) {
            numbers[i] = random.nextInt(max - min + 1) + min;
        }

        return numbers;
    }

    public static void main(String[] args) {
        List<String> listWords = new ArrayList<>();
        List<String> words = new ArrayList<>();
        listWords.add("remember");
        listWords.add("my friend");
        listWords.add("boredom");
        listWords.add("is a");
        listWords.add("crime");

        String path = "src\\main\\resources\\Words.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                listWords.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file" + e.getMessage());
        }

        int[] randomNumbers = generateRandomNumbers(10, 0, 104);
        for ( int i = 0 ; i < 10 ; i++)
            words.add(listWords.get(randomNumbers[i]));
        Thread thread = new Thread(() -> {
            try {
                typingTest(words);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Press enter to exit.");
        });

        thread.start();
    }
}