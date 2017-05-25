package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameConstants.HomeStatus;
import survivor.model.gameElements.items.ContainableItem;
import survivor.model.gameElements.sections.Section;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

import java.util.ArrayList;

import static survivor.model.gameConstants.Messages.INCORRECT;

public class HomeLivingRoom extends Section {
    private static final Logger LOG = Logger.getLogger(HomeLivingRoom.class);

    private final int CUPBOARD_EMPTY = 1;

    public HomeLivingRoom() {
        super(HomeStatus.LIVING_ROOM, true, true);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_LIVING_ROOM);

        sectionThings.add(new ContainableItem(CUPBOARD, new ArrayList<>(), true, Files.LIVING_ROOM_CUPBOARD));
        sectionThings.add(new ContainableItem(WINDOW, new ArrayList<>(), false, Files.LIVING_ROOM_WINDOW));

        addToAllDescriptions(SECTION_NAME, sectionDescriptions);
        addToAllDescriptions(CUPBOARD, getAllDescriptionsOfThink(CUPBOARD));
        addToAllDescriptions(WINDOW, getAllDescriptionsOfThink(WINDOW));
    }

    @Override
    public String north(String command) {
        Game.status = HomeStatus.BEDROOM;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String west(String command) {
        Game.status = HomeStatus.HALLWAY;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String inspect(String item) {
        if (item.equals(WINDOW))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(WINDOW))
                return getThingDescription(WINDOW, DESCRIPTION);

        if (item.equals(CUPBOARD))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD))
                return getThingDescription(CUPBOARD, DESCRIPTION);

        return INCORRECT;
    }

    @Override
    public String open(String item) {
        if (item.equals(NO_NAME)) {
            if (isLastMessageWasOfThink(CUPBOARD, DESCRIPTION))
                return getThingDescription(CUPBOARD, CUPBOARD_EMPTY);
        }

        if (item.equals(CUPBOARD))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD))
                return getThingDescription(CUPBOARD, CUPBOARD_EMPTY);

        return INCORRECT;
    }
}