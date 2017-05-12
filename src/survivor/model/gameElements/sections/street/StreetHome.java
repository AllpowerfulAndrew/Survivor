package survivor.model.gameElements.sections.street;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameStatus.StreetStatus;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

public class StreetHome extends Section {
    private static final Logger LOG = Logger.getLogger(StreetHome.class);

    public StreetHome() {
        super(StreetStatus.HOME, false, false);
        sectionDescriptions = Reader.readLocationFromFile(Files.STREET_HOME);
    }

    public String otherInteraction(String[] command) {
        if (command.length == ONE) return oneCommand(command[FIRST]);
        if (command.length == TWO) return twoCommand(command);

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String oneCommand(String command) {
        if ((command.equals(NORTH) || command.equals(NORTH_S)) && Game.status.equals(StreetStatus.HOME)) {
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = StreetStatus.CROSSROADS;
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