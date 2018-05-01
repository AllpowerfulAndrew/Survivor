package game.model.elements.items;

import game.model.processing.Reader;

import java.util.ArrayList;

import static game.model.constants.Messages.INCORRECT;

public class ContainableItem extends Item {
    public ArrayList<TakeableItem> items;
    public boolean isOpen;
    private ArrayList<String> description;

    public ContainableItem(String name, ArrayList<TakeableItem> items, boolean isOpen, String file) {
        super(name, file);
        this.items = items;
        this.isOpen = isOpen;
        description = Reader.readLocationFromFile(file);
    }

    public TakeableItem takeAnItem(String name) {
        TakeableItem item = null;

        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).name.equals(name)) {
                item = items.get(i);
                items.remove(i);
                break;
            }
        }

        return item;
    }

    public String getAnItemDescription(String name, int num) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).name.equals(name)) {
                return items.get(i).getDescription(num);
            }
        }

        return INCORRECT;
    }

    public boolean ifItemExist(String name) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).name.equals(name)) {
                return true;
            }
        }

        return false;
    }

    public String getDescription(int num) {
        return description.get(num);
    }

    public ArrayList<String> getDescriptionsList() {
        return description;
    }

}