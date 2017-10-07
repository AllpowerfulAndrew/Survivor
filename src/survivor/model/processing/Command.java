package survivor.model.processing;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameConstants.HomeStatus;
import survivor.model.gameConstants.StreetStatus;
import survivor.model.gameElements.items.TakeableItem;

import static survivor.model.gameBasics.Game.isTimingOn;
import static survivor.model.gameBasics.Player.*;

public abstract class Command {
    private static final Logger LOG = Logger.getLogger(Command.class);
    public static String penultimateInput = "";

    public static String getResponse(String command) {
        String[] list = command.split(" ");

        //debug
        debug(command);

        return Game.mainInteraction(list);
    }

    private static void debug(String command) {
        if (command.equals("всё")) {
            hasClock = true;
            hasThermometer = true;
        }
        if (command.equals("запустить время")) isTimingOn = true;
        if (command.equals("остановить время")) isTimingOn = false;
        if (command.equals("показать часы")) hasClock = true;
        if (command.equals("скрыть часы")) hasClock = false;
        if (command.equals("показать градусы")) hasThermometer = true;
        if (command.equals("скрыть градусы")) hasThermometer = false;
        if (command.equals("улица")) Game.status = StreetStatus.HOME;
        if (command.equals("крыша")) Game.status = HomeStatus.ROOF;
        if (command.equals("лестницы")) Game.status = HomeStatus.STAIRCASE_UP;
        if (command.equals("топор")) putInInventory(new TakeableItem("топор", 4, true, "noFile"));
    }
}