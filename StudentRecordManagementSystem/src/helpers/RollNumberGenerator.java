package helpers;

import java.io.*;

public class RollNumberGenerator {
    private static long nextNumber = 1;
    private static final long MAX_NUMBER = 99999;
    private static final String FILE_PATH = "nextNumber.txt";

    static {
        // Load the last generated number from the file, if available
        loadNextNumber();
    }

    private static void loadNextNumber() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                nextNumber = Long.parseLong(line);
            }
        } catch (IOException | NumberFormatException e) {
            // Handle file IO exception or parsing exception
            e.printStackTrace();
        }
    }

    public static synchronized long getNextNumber() {
        long currentNumber = nextNumber;

        // Increment the next number
        nextNumber++;
        if (nextNumber > MAX_NUMBER) {
            nextNumber = 1; // Reset to 1 if the maximum number is reached
        }

        // Save the next number to the file for persistence
        saveNextNumber();

        return currentNumber;
    }

    private static void saveNextNumber() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(Long.toString(nextNumber));
        } catch (IOException e) {
            // Handle file IO exception
            e.printStackTrace();
        }
    }
    
    public static synchronized void resetNextNumber(long newNumber) {
        if (newNumber >= 1 && newNumber <= MAX_NUMBER) {
            nextNumber = newNumber;
            saveNextNumber(); // Save the new number to the file
        } else {
            System.out.println("Invalid number. Please provide a number between 1 and " + MAX_NUMBER);
        }
    }
}
