package game.model.elements.locations;

import game.model.constants.StreetStatus;
import game.model.elements.sections.street.StreetCrossroads;
import game.model.elements.sections.street.StreetHome;

public class Street extends Location {
    {
        sectionsId.add(StreetStatus.HOME);
        sectionsId.add(StreetStatus.CROSSROADS);
    }

    public Street() {
        super("Street");
        sections.add(new StreetHome());
        sections.add(new StreetCrossroads());
    }
}