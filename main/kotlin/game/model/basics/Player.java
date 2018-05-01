package game.model.basics;

import game.model.elements.items.TakeableItem;

import java.util.ArrayList;

import static game.model.constants.Messages.ITEM_NOT_EXIST;

public abstract class Player {
    public static String location;
    private static ArrayList<TakeableItem> inventory = new ArrayList<>();

    public static String showInventory() {
        String list = "";

        if (inventory.isEmpty()) return "Инвентарь пуст...";

        for (TakeableItem item : inventory) {
            list += item.name + " (" + item.weight + " кг)\n";
        }

        return list;
    }

    public static void putInInventory(TakeableItem item) {
        if (inventory == null) inventory = new ArrayList<>();
        inventory.add(item);
    }

    public static String getItemDescription(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(name)) return inventory.get(i).getDescription(0);
        }

        return ITEM_NOT_EXIST;
    }

    public static boolean doesHaveItem(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(name)) {
                return true;
            }
        }

        return false;
    }

    public static TakeableItem dropAnItem(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(name)) {
                TakeableItem item = inventory.get(i);
                inventory.remove(i);
                return item;
            }
        }

        throw new IllegalArgumentException();
    }

    public static void deleteItem(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(name)) inventory.remove(i);
        }
    }
}