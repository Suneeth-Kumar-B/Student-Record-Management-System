package helpers;

import java.io.*;

public class LoginIdGenerator {
    private static final String FILE_PATH = "login_id.txt";
    private static int currentNumber;

    static {
        // Initialize currentNumber from file
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                currentNumber = Integer.parseInt(line);
            } else {
                currentNumber = 0;
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); // Handle file reading errors
            currentNumber = 0; // Reset to default value
        }
    }

    public synchronized static int getNextNumber() {
        currentNumber = (currentNumber % 100) + 1;
        // Update file with the new currentNumber
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(String.valueOf(currentNumber));
        } catch (IOException e) {
            e.printStackTrace(); // Handle file writing errors
        }
        return currentNumber;
    }
}
