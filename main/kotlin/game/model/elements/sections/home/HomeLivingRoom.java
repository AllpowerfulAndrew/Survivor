package game.model.elements.sections.home;

import game.model.basics.Game;
import game.model.constants.HomeStatus;
import game.model.elements.items.ContainableItem;
import game.model.elements.sections.Section;
import game.model.processing.Files;
import game.model.processing.Reader;

import java.util.ArrayList;

import static game.model.constants.Messages.INCORRECT;
import static game.model.elements.Elements.*;

public class HomeLivingRoom extends Section {
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
        if (item.equals(NO_NAME))
            if (isLastMessageWasOfThink(CUPBOARD, DESCRIPTION))
                return getThingDescription(CUPBOARD, CUPBOARD_EMPTY);

        if (item.equals(CUPBOARD))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD))
                return getThingDescription(CUPBOARD, CUPBOARD_EMPTY);

        return INCORRECT;
    }
}