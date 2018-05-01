package game.model.elements.sections.home;

import game.model.basics.Game;
import game.model.constants.HomeStatus;
import game.model.elements.sections.Section;
import game.model.processing.Files;
import game.model.processing.Reader;

public class HomeStaircaseDown extends Section {
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