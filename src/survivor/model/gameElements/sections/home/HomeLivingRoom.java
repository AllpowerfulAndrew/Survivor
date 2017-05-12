package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameElements.items.ContainableItem;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameStatus.HomeStatus;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

import java.util.ArrayList;

public class HomeLivingRoom extends Section {
    private static final Logger LOG = Logger.getLogger(HomeLivingRoom.class);

    private final int CUPBOARD_EMPTY = 1;

    public HomeLivingRoom() {
        super(HomeStatus.LIVING_ROOM, true, true);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_LIVING_ROOM);

        sectionThings.add(new ContainableItem(CUPBOARD, new ArrayList<>(), true, Files.LIVING_ROOM_CUPBOARD));
        sectionThings.add(new ContainableItem(WINDOW, new ArrayList<>(), false, Files.LIVING_ROOM_WINDOW));

        addToAllDescriptions(SECTION, sectionDescriptions);
        addToAllDescriptions(CUPBOARD, getAllDescriptionsOfThink(CUPBOARD));
        addToAllDescriptions(WINDOW, getAllDescriptionsOfThink(WINDOW));
    }

    public String otherInteraction(String[] command) {
        if (command.length == ONE) return oneCommand(command[FIRST]);
        if (command.length == TWO) return twoCommand(command);

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String oneCommand(String command) {
        if ((command.equals(NORTH) || command.equals(NORTH_S)) && Game.status.equals(HomeStatus.LIVING_ROOM))
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.BEDROOM;
                return Game.mainInteraction(new String[]{command});
            }

        if ((command.equals(WEST) || command.equals(WEST_S)) && Game.status.equals(HomeStatus.LIVING_ROOM))
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.HALLWAY;
                return Game.mainInteraction(new String[]{command});
            }

        if (command.equals(SOUTH) || command.equals(SOUTH_S) || command.equals(EAST) || command.equals(EAST_S))
            if (isSectionDescriptionWasLastMessage())
                return getSectionDescription(NO_WAY);

        if (command.equals(OPEN) || command.equals(OPEN_S))
            if (isLastMessageWasOfThink(CUPBOARD, DESCRIPTION))
                return getThingDescription(CUPBOARD, CUPBOARD_EMPTY);

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String twoCommand(String[] command) {
        if (command[FIRST].equals(INSPECT) || command[FIRST].equals(INSPECT_S)) {
            if (command[SECOND].equals(WINDOW))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(WINDOW))
                    return getThingDescription(WINDOW, DESCRIPTION);

            if (command[SECOND].equals(CUPBOARD))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD))
                    return getThingDescription(CUPBOARD, DESCRIPTION);
        }

        if (command[FIRST].equals(OPEN) || command[FIRST].equals(OPEN_S))
            if (command[SECOND].equals(CUPBOARD))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD))
                    return getThingDescription(CUPBOARD, CUPBOARD_EMPTY);

        LOG.warn("Ошибочная команда!");
        return Game.incorrect;
    }
}