package game.model.elements.sections.home;

import game.model.basics.Game;
import game.model.constants.HomeStatus;
import game.model.elements.items.ContainableItem;
import game.model.elements.items.TakeableItem;
import game.model.elements.sections.Section;
import game.model.processing.Files;
import game.model.processing.Reader;

import java.util.ArrayList;

import static game.model.constants.Messages.INCORRECT;
import static game.model.elements.Elements.*;

public class HomeBedroom extends Section {
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