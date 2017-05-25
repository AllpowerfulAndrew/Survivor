package survivor.model.gameElements.locations;

import survivor.model.gameElements.sections.home.*;
import survivor.model.gameConstants.HomeStatus;

public class Home extends Location {
    {
        sectionsId.add(HomeStatus.BEDROOM);
        sectionsId.add(HomeStatus.LIVING_ROOM);
        sectionsId.add(HomeStatus.HALLWAY);
        sectionsId.add(HomeStatus.BATHROOM);
        sectionsId.add(HomeStatus.KITCHEN);
        sectionsId.add(HomeStatus.STAIRCASE_DOWN);
        sectionsId.add(HomeStatus.STAIRCASE_UP);
        sectionsId.add(HomeStatus.ROOF);
    }

    public Home() {
        super("Home");
        sections.add(new HomeBedroom());
        sections.add(new HomeLivingRoom());
        sections.add(new HomeHallway());
        sections.add(new HomeBathroom());
        sections.add(new HomeKitchen());
        sections.add(new HomeStaircaseDown());
        sections.add(new HomeStaircaseUp());
        sections.add(new HomeRoof());
    }
}