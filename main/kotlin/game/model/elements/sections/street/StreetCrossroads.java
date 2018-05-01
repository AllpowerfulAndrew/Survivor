package game.model.elements.sections.street;

import game.model.basics.Game;
import game.model.constants.StreetStatus;
import game.model.elements.sections.Section;
import game.model.processing.Files;
import game.model.processing.Reader;

public class StreetCrossroads extends Section {
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