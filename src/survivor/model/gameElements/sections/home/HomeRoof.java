package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameConstants.HomeStatus;
import survivor.model.gameConstants.StreetStatus;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

import static survivor.model.gameBasics.Game.isTimingOn;

public class HomeRoof extends Section {
    private static final Logger LOG = Logger.getLogger(HomeRoof.class);

    public HomeRoof() {
        super(HomeStatus.ROOF, false, false);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_ROOF);

        addToAllDescriptions(SECTION_NAME, sectionDescriptions);
    }

    @Override
    public String south(String command) {
        Game.status = HomeStatus.STAIRCASE_UP;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String east(String command) {
        Game.status = StreetStatus.HOME;
        return Game.mainInteraction(new String[]{command});
    }
}