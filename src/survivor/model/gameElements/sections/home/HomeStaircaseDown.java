package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameConstants.HomeStatus;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

public class HomeStaircaseDown extends Section {
    private static final Logger LOG = Logger.getLogger(HomeStaircaseDown.class);

    public HomeStaircaseDown() {
        super(HomeStatus.STAIRCASE_DOWN, true, false);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_STAIRCASE_DOWN);

        addToAllDescriptions(SECTION_NAME, sectionDescriptions);
    }

    @Override
    public String north(String command) {
        Game.status = HomeStatus.HALLWAY;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String south(String command) {
        Game.status = HomeStatus.STAIRCASE_UP;
        return Game.mainInteraction(new String[]{command});
    }
}