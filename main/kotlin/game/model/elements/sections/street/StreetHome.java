package game.model.elements.sections.street;

import game.model.basics.Game;
import game.model.constants.StreetStatus;
import game.model.elements.sections.Section;
import game.model.processing.Files;
import game.model.processing.Reader;

import static game.model.basics.Game.isTimingOn;

public class StreetHome extends Section {
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