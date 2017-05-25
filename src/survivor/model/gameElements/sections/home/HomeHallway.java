package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameBasics.Player;
import survivor.model.gameConstants.HomeStatus;
import survivor.model.gameElements.items.ContainableItem;
import survivor.model.gameElements.sections.Section;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

import java.util.ArrayList;

import static survivor.model.gameConstants.Messages.INCORRECT;

public class HomeHallway extends Section {
    private static final Logger LOG = Logger.getLogger(HomeHallway.class);

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
        if (item.equals(NO_NAME)) {
            if (isLastMessageWasOfThink(DOOR, DESCRIPTION) || isLastMessageWasOfThink(DOOR, IS_LOCKED) ||
                    isLastMessageWasOfThink(DOOR, IS_OPEN))
                return openDoor();
        }

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
        LOG.info("Пытаемся открыть дверь");
        if (getThingByName(DOOR).isOpen) {
            LOG.info("Дверь уже открыта");
            return getThingDescription(DOOR, IS_OPEN);
        } else if (Player.doesHaveItem(KEY)) {
            LOG.info("Используем ключи");
            getThingByName(DOOR).isOpen = true;
            Player.deleteItem(KEY);
            return getThingDescription(DOOR, OPENING);
        } else {
            LOG.info("Дверь заперта");
            return getThingDescription(DOOR, IS_LOCKED);
        }
    }
}