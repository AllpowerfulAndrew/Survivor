package game.model.elements.sections.home;

import game.model.basics.Game;
import game.model.basics.Player;
import game.model.constants.HomeStatus;
import game.model.elements.items.ContainableItem;
import game.model.elements.sections.Section;
import game.model.processing.Files;
import game.model.processing.Reader;

import java.util.ArrayList;

import static game.model.constants.Messages.INCORRECT;
import static game.model.elements.Elements.*;

public class HomeHallway extends Section {

    private final int IS_LOCKED = 1;
    private final int OPENING = 2;
    private final int IS_OPEN = 3;

    public HomeHallway() {
        super(HomeStatus.HALLWAY, true, true);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_HALLWAY);

        sectionThings.add(new ContainableItem(DOOR, new ArrayList<>(), false, Files.HALLWAY_DOOR));

        addToAllDescriptions(SECTION_NAME, sectionDescriptions);
        addToAllDescriptions(DOOR, getAllDescriptionsOfThink(DOOR));
    }

    @Override
    public String north(String command) {
        Game.status = HomeStatus.BATHROOM;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String south(String command) {
        if (!getThingByName(DOOR).isOpen) return getThingDescription(DOOR, IS_LOCKED);

        Game.status = HomeStatus.STAIRCASE_DOWN;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String west(String command) {
        Game.status = HomeStatus.KITCHEN;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String east(String command) {
        Game.status = HomeStatus.LIVING_ROOM;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String inspect(String item) {
        if (item.equals(DOOR))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(DOOR))
                return getThingDescription(DOOR, DESCRIPTION);

        return INCORRECT;
    }

    @Override
    public String open(String item) {
        if (item.equals(NO_NAME))
            if (isLastMessageWasOfThink(DOOR, DESCRIPTION) || isLastMessageWasOfThink(DOOR, IS_LOCKED) ||
                  isLastMessageWasOfThink(DOOR, IS_OPEN))
                return openDoor();

        if (item.equals(DOOR))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(DOOR))
                return openDoor();

        return INCORRECT;
    }

    @Override
    public String use(String item) {
        if (item.equals(KEY) && Player.doesHaveItem(KEY))
            if (isLastMessageWasDescriptionOf(DOOR))
                return openDoor();

        return INCORRECT;
    }

    private String openDoor() {
        if (getThingByName(DOOR).isOpen) {
            return getThingDescription(DOOR, IS_OPEN);
        } else if (Player.doesHaveItem(KEY)) {
            getThingByName(DOOR).isOpen = true;
            Player.deleteItem(KEY);
            return getThingDescription(DOOR, OPENING);
        } else {
            return getThingDescription(DOOR, IS_LOCKED);
        }
    }
}