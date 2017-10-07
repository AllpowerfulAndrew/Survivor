package survivor.model.gameElements.sections.street;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameConstants.StreetStatus;
import survivor.model.gameElements.sections.Section;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

public class StreetCrossroads extends Section {
    private static final Logger LOG = Logger.getLogger(StreetCrossroads.class);

    public StreetCrossroads() {
        super(StreetStatus.CROSSROADS, false, false);
        sectionDescriptions = Reader.readLocationFromFile(Files.STREET_CROSSROADS);
    }

    @Override
    public String south(String command) {
        Game.status = StreetStatus.HOME;
        return Game.mainInteraction(new String[]{command});
    }
}