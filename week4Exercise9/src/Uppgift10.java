import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Uppgift10 {

    public static void main(String[] args) throws IOException {

        User user1 = new User();

        user1.name = "Robin Larsson";
        user1.id = 880401;
        user1.city = "Malmö";
        user1.zipcode = "21611";
        user1.likes = Arrays.asList("DOTA", "Power King", "Anna-Karin", "Katter", "Mamma");
        user1.deactivated = false;


        // Serialization
        Gson gson = new Gson();
        String json = gson.toJson(user1);

        System.out.println(String.format("Serialization: %s\n", json));

        // Pretty Printing
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson2.toJson(user1);

        System.out.println(String.format("Pretty Printing: %s\n", jsonOutput));

        // Deserialization
        User user2 = gson.fromJson(json, User.class);

        System.out.println(String.format("Deserialization: %s\n", user2.toString()));


        // Print json object to file
        try (FileWriter file = new FileWriter("/Users/AK/Avancerad Java/file1.txt")) {
            file.write(jsonOutput);
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print json object to file without catch
        try(FileWriter file2 = new FileWriter("/Users/AK/Avancerad Java/file2.txt")) {
            file2.write(json);
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + json);
        }

        // Deserialization from file
        System.out.println("Deserialization from file");
        User user3 = gson2.fromJson(new FileReader("/Users/AK/Avancerad Java/file1.txt"), User.class);

        System.out.println(user3.toString());

    }
}
