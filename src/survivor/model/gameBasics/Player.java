package survivor.model.gameBasics;

import survivor.model.gameElements.items.Item;

import java.util.ArrayList;

public abstract class Player {
    public static boolean hasClock;
    public static boolean hasThermometer;
    public static String location;
    private static ArrayList<Item> inventory = new ArrayList<>();
    private static int health;

    public static String showInventory() {
        String list = "";

        if (inventory.isEmpty()) return "Инвентарь пуст...";

        for (Item item : inventory) {
            list += item.name + " (" + item.weight + " кг)\n";
        }

        return list;
    }

    public static void putInInventory(Item item) {
        if (inventory == null) inventory = new ArrayList<>();
        inventory.add(item);
    }

    public static String getItemDescription(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(name)) return inventory.get(i).getDescription(0);
        }

        return "Такого предмета нет в инвентаре...";
    }

    public static boolean doesHaveItem(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(name)) return true;
        }

        return false;
    }

    public static Item dropAnItem(String name) {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(name)) {
                Item item = inventory.get(i);
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

    public static void setHealth(int delta) {
        health = health + delta;
        if (health > 100) health = 100;
        if (health < 0) health = 0;
    }

    public static int getHealth() {
        return health;
    }
}