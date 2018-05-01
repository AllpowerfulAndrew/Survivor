package game.model.processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Reader {
    public static ArrayList<String> readLocationFromFile(String path) {
        String line;
        ArrayList<String> text = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(new File("content/" + path + ".txt")))) {
            while ((line = reader.readLine()) != null) {
                text.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text;
    }
}