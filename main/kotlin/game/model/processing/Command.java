package game.model.processing;

import game.model.basics.Game;
import game.model.constants.HomeStatus;
import game.model.constants.StreetStatus;
import game.model.elements.items.TakeableItem;

import static game.model.basics.Player.*;

public abstract class Command {
    public static String penultimateInput = "";

    public static String getResponse(String command) {
        String[] list = command.split(" ");

        //debug
        debug(command);

        return Game.mainInteraction(list);
    }

    private static void debug(String command) {
        if (command.equals("улица")) Game.status = StreetStatus.HOME;
        if (command.equals("крыша")) Game.status = HomeStatus.ROOF;
        if (command.equals("лестницы")) Game.status = HomeStatus.STAIRCASE_UP;
        if (command.equals("топор")) putInInventory(new TakeableItem("топор", 4, true, "noFile"));
    }
}