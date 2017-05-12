package survivor.model.gameElements.sections.street;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameStatus.StreetStatus;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

public class StreetCrossroads extends Section {
    private static final Logger LOG = Logger.getLogger(StreetCrossroads.class);

    public StreetCrossroads() {
        super(StreetStatus.CROSSROADS, false, false);
        sectionDescriptions = Reader.readLocationFromFile(Files.STREET_CROSSROADS);
    }

    public String otherInteraction(String[] command) {
        Game.isTimingOn = true;

        if (command.length == ONE) return oneCommand(command[FIRST]);
        if (command.length == TWO) return twoCommand(command);

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String oneCommand(String command) {
        if ((command.equals(SOUTH) || command.equals(SOUTH_S)) && Game.status.equals(StreetStatus.CROSSROADS)) {
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = StreetStatus.HOME;
                return Game.mainInteraction(new String[]{command});
            }
        }

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String twoCommand(String[] command) {
        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

}
