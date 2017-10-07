package survivor.model.gameBasics;

import org.apache.log4j.Logger;
import survivor.model.gameElements.items.TakeableItem;

import java.util.ArrayList;
import java.util.Random;

import static survivor.model.gameConstants.Feeling.RESTED;
import static survivor.model.gameConstants.HealthStatus.FATAL_INJURED;
import static survivor.model.gameConstants.HealthStatus.INJURED;
import static survivor.model.gameConstants.Messages.ITEM_NOT_EXIST;

public abstract class Player {
    private static final Logger LOG = Logger.getLogger(Player.class);

    public static boolean hasClock;
    public static boolean hasThermometer;
    public static boolean isAwake = true;
    public static String location;
    private static ArrayList<TakeableItem> inventory = new ArrayList<>();
    private static int health = 100;
    private static String healthStatus;
    private static String feelStatus = RESTED;
    private static double feelingParameter = 100;


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
        LOG.info("Проверяем, есть ли такой предмет в инвентаре.");
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).name.equals(name)) {
                LOG.info("Такой предмет есть.");
                return true;
            }
        }

        LOG.info("Такого предмета нет.");
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

    public static void setHealth(int delta) {
        health += delta;
        if (health > 100) health = 100;
        if (health < 0) health = 0;
    }

    public static int getHealth() {
        return health;
    }

    public static void increaseFeelingParameter() {
        double parameter;
        parameter = feelingParameter + Game.difficulty + 11.5;

        if (parameter > 100) feelingParameter = 100;
        else feelingParameter = parameter;
    }

    public static void decreaseFeelingParameter() {
        double parameter;
        parameter = feelingParameter - Game.difficulty * new Random().nextDouble();

        if (parameter < 100) feelingParameter = 0;
        else feelingParameter = parameter;
    }

    public void injured() {
        if (healthStatus.equals(INJURED)) healthStatus = FATAL_INJURED;
        else healthStatus = INJURED;
    }

    private void setFeeling() {

    }

    private void modHealth(int mod) {
        health += mod;
        if (health > 100) health = 100;
        if (health < 100) health = 0;
    }
}