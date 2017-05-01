package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameElements.Elements;
import survivor.model.gameElements.items.CommonItem;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameElements.things.CommonThing;
import survivor.model.gameStatus.HomeStatus;
import survivor.model.processing.*;

import java.util.ArrayList;

public class HomeBedroom extends Section implements Elements, Commands {
    private static final Logger LOG = Logger.getLogger(HomeBedroom.class);

    public HomeBedroom() {
        super(HomeStatus.BEDROOM, true, true);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_BEDROOM);

        addSectionItem(new CommonItem(CLOCK, 3, Files.BEDROOM_CLOCK));
        sectionThings.add(new CommonThing(WINDOW, new ArrayList<>(), false, Files.BEDROOM_WINDOW));
        sectionThings.add(new CommonThing(BED, new ArrayList<>(), false, Files.BEDROOM_BED));

        addToAllDescriptions(SECTION, sectionDescriptions);
        addToAllDescriptions(WINDOW, getAllDescriptionsOfThink(WINDOW));
        addToAllDescriptions(BED, getAllDescriptionsOfThink(BED));
        addToAllDescriptions(CLOCK, getAllDescriptionsOfItem(CLOCK));
    }

    public String otherInteraction(String[] command) {
        if (command.length == ONE) return oneCommand(command[FIRST]);
        if (command.length == TWO) return twoCommand(command);

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String oneCommand(String command) {
        if ((command.equals(SOUTH) || command.equals(SOUTH_S)) && Game.status.equals(HomeStatus.BEDROOM))
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.LIVING_ROOM;
                return Game.mainInteraction(new String[]{command});
            }

        if (command.equals(NORTH) || command.equals(NORTH_S) || command.equals(WEST) ||
                command.equals(WEST_S) || command.equals(EAST) || command.equals(EAST_S))
            if (isSectionDescriptionWasLastMessage())
                return getSectionDescription(NO_WAY);

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String twoCommand(String[] command) {
        if (command[FIRST].equals(INSPECT) || command[FIRST].equals(INSPECT_S))
            if (isSectionDescriptionWasLastMessage()) {
                if (command[SECOND].equals(WINDOW)) return getThingDescription(WINDOW, DESCRIPTION);
                if (command[SECOND].equals(BED)) return getThingDescription(BED, DESCRIPTION);
                if (command[SECOND].equals(CLOCK)) return getSectionItemDescription(CLOCK, DESCRIPTION);
            }

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }
}