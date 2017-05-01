package survivor.model.gameElements.items;

import survivor.model.processing.Reader;

import java.util.ArrayList;

public abstract class Item {
    public String name;
    public double weight;
    private String file;
    private ArrayList<String> descriptions;

    public Item(String name, double weight, String file) {
        this.name = name;
        this.weight = weight;
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
