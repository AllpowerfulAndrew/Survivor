package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameElements.Elements;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameStatus.HomeStatus;
import survivor.model.gameStatus.StreetStatus;
import survivor.model.processing.*;

public class HomeRoof extends Section implements Elements, Commands {
    private static final Logger LOG = Logger.getLogger(HomeRoof.class);

    public HomeRoof() {
        super(HomeStatus.ROOF, false, false);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_ROOF);

        addToAllDescriptions(SECTION, sectionDescriptions);
    }

    public String otherInteraction(String[] command) {
        if (command.length == ONE) return oneCommand(command[FIRST]);
        if (command.length == TWO) return twoCommand(command);

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String oneCommand(String command) {
        LOG.info("Выполняем одиночную команду");
        if ((command.equals(SOUTH) || command.equals(SOUTH_S)) && Game.status.equals(HomeStatus.ROOF)) {
            LOG.info("Направляемся на юг");
            if (isSectionDescriptionWasLastMessage()) {
                LOG.info("Направляемся на юг");
                Game.status = HomeStatus.STAIRCASE_UP;
                return Game.mainInteraction(new String[]{command});
            }
        }

        if ((command.equals(EAST) || command.equals(EAST_S)) && Game.status.equals(HomeStatus.ROOF)) {
            LOG.info("Направляемся на восток");
            if (isSectionDescriptionWasLastMessage()) {
                LOG.info("Меняем статус на " + StreetStatus.HOME);
                Game.status = StreetStatus.HOME;
                return Game.mainInteraction(new String[]{command});
            }
        }

        if (command.equals(NORTH) || command.equals(NORTH_S) || command.equals(WEST) || command.equals(WEST_S))
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