package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameElements.Elements;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameStatus.HomeStatus;
import survivor.model.processing.Commands;
import survivor.model.processing.Files;
import survivor.model.gameBasics.Game;
import survivor.model.processing.Reader;

public class HomeStaircaseDown extends Section implements Elements, Commands {
    private static final Logger LOG = Logger.getLogger(HomeStaircaseDown.class);

    public HomeStaircaseDown() {
        super(HomeStatus.STAIRCASE_DOWN, true, false);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_STAIRCASE_DOWN);

        addToAllDescriptions(SECTION, sectionDescriptions);
    }

    public String otherInteraction(String[] command) {
        if (command.length == ONE) return oneCommand(command[FIRST]);
        if (command.length == TWO) return twoCommand(command);

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String oneCommand(String command) {
        if ((command.equals(NORTH) || command.equals(NORTH_S)) && Game.status.equals(HomeStatus.STAIRCASE_DOWN))
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.HALLWAY;
                return Game.mainInteraction(new String[]{command});
            }

        if ((command.equals(SOUTH) || command.equals(SOUTH_S)) && Game.status.equals(HomeStatus.STAIRCASE_DOWN)) {
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.STAIRCASE_UP;
                return Game.mainInteraction(new String[]{command});
            }
        }

        if (command.equals(WEST) || command.equals(WEST_S) || command.equals(EAST) || command.equals(EAST_S))
            if (isSectionDescriptionWasLastMessage())
                return getSectionDescription(NO_WAY);

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String twoCommand(String[] command) {
        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }
}