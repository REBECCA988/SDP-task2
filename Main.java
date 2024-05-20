import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Specify the file name (or path if it's not in the same directory as the program)
        String fileName = "data.xml";

        // Use try-with-resources to ensure the file is closed after reading
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            // Read each line from the file until the end
            while ((line = br.readLine()) != null) {
                // Print the line
                System.out.println(line);
            }
        } catch (IOException e) {
            // Handle potential IOException
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}

