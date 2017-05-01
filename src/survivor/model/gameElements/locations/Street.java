package survivor.model.gameElements.locations;

import survivor.model.gameElements.sections.street.StreetCrossroads;
import survivor.model.gameElements.sections.street.StreetHome;
import survivor.model.gameStatus.StreetStatus;

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
