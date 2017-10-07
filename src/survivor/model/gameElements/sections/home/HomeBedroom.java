package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameConstants.HomeStatus;
import survivor.model.gameElements.items.ContainableItem;
import survivor.model.gameElements.items.TakeableItem;
import survivor.model.gameElements.sections.Section;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

import java.util.ArrayList;

import static survivor.model.gameConstants.Messages.INCORRECT;
import static survivor.model.gameElements.Elements.*;

public class HomeBedroom extends Section {
    private static final Logger LOG = Logger.getLogger(HomeBedroom.class);

    public HomeBedroom() {
        super(HomeStatus.BEDROOM, true, true);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_BEDROOM);

        addSectionItem(new TakeableItem(CLOCK, 3, false, Files.BEDROOM_CLOCK));
        sectionThings.add(new ContainableItem(WINDOW, new ArrayList<>(), false, Files.BEDROOM_WINDOW));
        sectionThings.add(new ContainableItem(BED, new ArrayList<>(), false, Files.BEDROOM_BED));

        addToAllDescriptions(SECTION_NAME, sectionDescriptions);
        addToAllDescriptions(WINDOW, getAllDescriptionsOfThink(WINDOW));
        addToAllDescriptions(BED, getAllDescriptionsOfThink(BED));
        addToAllDescriptions(CLOCK, getAllDescriptionsOfItem(CLOCK));
    }

    @Override
    public String south(String command) {
        Game.status = HomeStatus.LIVING_ROOM;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String inspect(String item) {
        if (isSectionDescriptionWasLastMessage()) {
            if (item.equals(WINDOW)) return getThingDescription(WINDOW, DESCRIPTION);
            if (item.equals(BED)) return getThingDescription(BED, DESCRIPTION);
            if (item.equals(CLOCK)) return getSectionItemDescription(CLOCK, DESCRIPTION);
        }

        return INCORRECT;
    }
}