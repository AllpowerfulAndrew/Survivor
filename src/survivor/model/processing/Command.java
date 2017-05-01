package survivor.model.processing;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameBasics.Player;
import survivor.model.gameElements.items.CommonItem;
import survivor.model.gameStatus.HomeStatus;
import survivor.model.gameStatus.StreetStatus;

public abstract class Command implements Commands {
    private static final Logger LOG = Logger.getLogger(Command.class);
    public static String penultimateInput = "";

    public static String getResponse(String command) {
        String[] list = command.split(" ");

        //debug
        debug(command);

        return Game.mainInteraction(list);
    }

    private static void debug(String command) {
        if (command.equals("запустить время")) Game.isTimingOn = true;
        if (command.equals("остановить время")) Game.isTimingOn = false;
        if (command.equals("показать часы")) Player.hasClock = true;
        if (command.equals("скрыть часы")) Player.hasClock = false;
        if (command.equals("показать градусы")) Player.hasThermometer = true;
        if (command.equals("скрыть градусы")) Player.hasThermometer = false;
        if (command.equals("улица")) Game.status = StreetStatus.HOME;
        if (command.equals("крыша")) Game.status = HomeStatus.ROOF;
        if (command.equals("лестницы")) Game.status = HomeStatus.STAIRCASE_UP;
        if (command.equals("топор")) Player.putInInventory(new CommonItem("топор", 4, "noFile"));
    }
}
