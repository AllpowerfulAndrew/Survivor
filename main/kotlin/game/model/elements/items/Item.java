package game.model.elements.items;

import game.model.processing.Reader;

import java.util.ArrayList;

public abstract class Item {
    public String name;
    private String file;
    private ArrayList<String> descriptions;

    public Item(String name, String file) {
        this.name = name;
        this.file = file;
        descriptions = Reader.readLocationFromFile(file);
    }

    public String getDescription(int num) {
        return descriptions.get(num);
    }

    public ArrayList<String> getDescriptionsList() {
        return descriptions;
    }
}