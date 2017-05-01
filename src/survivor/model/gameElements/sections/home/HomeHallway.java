package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameBasics.Player;
import survivor.model.gameElements.Elements;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameElements.things.CommonThing;
import survivor.model.gameStatus.HomeStatus;
import survivor.model.processing.*;

import java.util.ArrayList;

public class HomeHallway extends Section implements Elements, Commands {
    private static final Logger LOG = Logger.getLogger(HomeHallway.class);

    private final int IS_LOCKED = 1;
    private final int OPENING = 2;
    private final int IS_OPEN = 3;

    public HomeHallway() {
        super(HomeStatus.HALLWAY, true, true);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_HALLWAY);

        sectionThings.add(new CommonThing(DOOR, new ArrayList<>(), false, Files.HALLWAY_DOOR));

        addToAllDescriptions(SECTION, sectionDescriptions);
        addToAllDescriptions(DOOR, getAllDescriptionsOfThink(DOOR));
    }

    public String otherInteraction(String[] command) {
        if (command.length == ONE) return oneCommand(command[FIRST]);
        if (command.length == TWO) return twoCommand(command);

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String oneCommand(String command) {
        if ((command.equals(EAST) || command.equals(EAST_S)) && Game.status.equals(HomeStatus.HALLWAY))
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.LIVING_ROOM;
                return Game.mainInteraction(new String[]{command});
            }

        if ((command.equals(NORTH) || command.equals(NORTH_S)) && Game.status.equals(HomeStatus.HALLWAY))
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.BATHROOM;
                return Game.mainInteraction(new String[]{command});
            }

        if ((command.equals(SOUTH) || command.equals(SOUTH_S)) && Game.status.equals(HomeStatus.HALLWAY)) {
            if (!getThingByName(DOOR).isOpen) return getThingDescription(DOOR, IS_LOCKED);

            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.STAIRCASE_DOWN;
                return Game.mainInteraction(new String[]{command});
            }
        }

        if (command.equals(WEST) || command.equals(WEST_S) && Game.status.equals(HomeStatus.HALLWAY))
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.KITCHEN;
                return Game.mainInteraction(new String[]{command});
            }

        if (command.equals(OPEN) || command.equals(OPEN_S))
            if (isLastMessageWasOfThink(DOOR, DESCRIPTION) ||
                    isLastMessageWasOfThink(DOOR, IS_LOCKED) ||
                    isLastMessageWasOfThink(DOOR, IS_OPEN))
                return openDoor();

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String twoCommand(String[] command) {
        if (command[FIRST].equals(INSPECT) || command[FIRST].equals(INSPECT_S))
            if (command[SECOND].equals(DOOR))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(DOOR))
                    return getThingDescription(DOOR, DESCRIPTION);

        if (command[FIRST].equals(OPEN) || command[FIRST].equals(OPEN_S))
            if (command[SECOND].equals(DOOR))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(DOOR))
                    return openDoor();

        if (command[FIRST].equals(USE) || command[FIRST].equals(USE_S))
            if (command[SECOND].equals(KEY) && Player.doesHaveItem(KEY))
                if (isLastMessageWasDescriptionOf(DOOR))
                    return openDoor();

        LOG.warn("Ошибочная команда!");
        return Game.incorrect;
    }

    private String openDoor() {
        LOG.info("Пытаемся открыть дверь");
        if (getThingByName(DOOR).isOpen) {
            LOG.info("Дверь уже открыта");
            return getThingDescription(DOOR, IS_OPEN);
        } else if (Player.doesHaveItem(KEY)) {
            LOG.info("Используем ключи");
            getThingByName(DOOR).isOpen = true;
            Player.deleteItem(KEY);
            return getThingDescription(DOOR, OPENING);
        } else {
            LOG.info("Дверь заперта");
            return getThingDescription(DOOR, IS_LOCKED);
        }
    }
}