package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameElements.Elements;
import survivor.model.gameElements.items.ContainableItem;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameStatus.HomeStatus;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

import java.util.ArrayList;

public class HomeStaircaseUp extends Section {
    private static final Logger LOG = Logger.getLogger(HomeStaircaseUp.class);

    private final int DISABLED = 1;
    private final int USING = 2;
    private final int ENABLED = 1;

    public HomeStaircaseUp() {
        super(HomeStatus.STAIRCASE_UP, true, false);
        sectionThings.add(new ContainableItem(STAIRS, new ArrayList<>(), false, Files.STAIRCASE_UP_STAIRS));
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_STAIRCASE_UP);

        addToAllDescriptions(SECTION, sectionDescriptions);
        addToAllDescriptions(STAIRS, getAllDescriptionsOfThink(STAIRS));
    }

    public String otherInteraction(String[] command) {
        if (command.length == ONE) return oneCommand(command[FIRST]);
        if (command.length == TWO) return twoCommand(command);

        for (int z = 0; z < 1; z++)

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String oneCommand(String command) {
        if ((command.equals(SOUTH) || command.equals(SOUTH_S)) && Game.status.equals(HomeStatus.STAIRCASE_UP))
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.STAIRCASE_DOWN;
                return Game.mainInteraction(new String[]{command});
            }

        if ((command.equals(NORTH) || command.equals(NORTH_S)) && Game.status.equals(HomeStatus.STAIRCASE_UP)) {
            if (!getThingByName(Elements.STAIRS).isOpen) return getThingDescription(STAIRS, DISABLED);

            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.ROOF;
                return Game.mainInteraction(new String[]{command});
            }
        }

        if (command.equals(WEST) || command.equals(WEST_S) || command.equals(EAST) || command.equals(EAST_S))
            if (isSectionDescriptionWasLastMessage())
                return getSectionDescription(NO_WAY);


        if (command.equals(USE) || command.equals(USE_S))
            if (isLastMessageWasOfThink(STAIRS, DESCRIPTION) || isLastMessageWasOfThink(STAIRS, DISABLED))
                return useStairs();

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String twoCommand(String[] command) {
        if (command[FIRST].equals(INSPECT) || command[FIRST].equals(INSPECT_S))
            if (command[SECOND].equals(STAIRS))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(STAIRS))
                    return getThingDescription(STAIRS, DESCRIPTION);

        if (command[FIRST].equals(USE) || command[FIRST].equals(USE_S))
            if (command[SECOND].equals(STAIRS))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(STAIRS))
                    return useStairs();

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String useStairs() {
        if (isLastMessageWasOfThink(STAIRS, DESCRIPTION) ||
                isLastMessageWasOfThink(STAIRS, DISABLED)) {
            if (!getThingByName(STAIRS).isOpen) {
                getThingByName(STAIRS).isOpen = true;
                return getThingDescription(STAIRS, USING);
            } else return getThingDescription(STAIRS, ENABLED);
        }

        if (isSectionDescriptionWasLastMessage()) {
            if (!getThingByName(STAIRS).isOpen) {
                getThingByName(STAIRS).isOpen = true;

                return getThingDescription(STAIRS, USING);
            } else return getThingDescription(STAIRS, ENABLED);
        }

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }
}
