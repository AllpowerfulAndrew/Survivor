package survivor.model.gameElements.sections.street;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameConstants.StreetStatus;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

import static survivor.model.gameBasics.Game.isTimingOn;

public class StreetHome extends Section {
    private static final Logger LOG = Logger.getLogger(StreetHome.class);

    public StreetHome() {
        super(StreetStatus.HOME, false, false);
        sectionDescriptions = Reader.readLocationFromFile(Files.STREET_HOME);
    }

    @Override
    public String north(String command) {
        if (!isTimingOn) isTimingOn = true;
        Game.status = StreetStatus.CROSSROADS;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String take(String item) {
        return super.take(item);
    }
}