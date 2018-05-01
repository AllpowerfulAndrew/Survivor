package game.model.elements.sections.home;

import game.model.basics.Game;
import game.model.constants.HomeStatus;
import game.model.constants.StreetStatus;
import game.model.elements.sections.Section;
import game.model.processing.Files;
import game.model.processing.Reader;

public class HomeRoof extends Section {
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