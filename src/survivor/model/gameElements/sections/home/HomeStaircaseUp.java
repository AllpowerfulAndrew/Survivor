package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameConstants.HomeStatus;
import survivor.model.gameElements.Elements;
import survivor.model.gameElements.items.ContainableItem;
import survivor.model.gameElements.sections.Section;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

import java.util.ArrayList;

import static survivor.model.gameConstants.Messages.INCORRECT;

public class HomeStaircaseUp extends Section {
    private static final Logger LOG = Logger.getLogger(HomeStaircaseUp.class);

    private final int DISABLED = 1;
    private final int USING = 2;
    private final int ENABLED = 1;

    public HomeStaircaseUp() {
        super(HomeStatus.STAIRCASE_UP, true, false);
        sectionThings.add(new ContainableItem(STAIRS, new ArrayList<>(), false, Files.STAIRCASE_UP_STAIRS));
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_STAIRCASE_UP);

        addToAllDescriptions(SECTION_NAME, sectionDescriptions);
        addToAllDescriptions(STAIRS, getAllDescriptionsOfThink(STAIRS));
    }

    @Override
    public String north(String command) {
        if (!getThingByName(Elements.STAIRS).isOpen) return getThingDescription(STAIRS, DISABLED);

        Game.status = HomeStatus.ROOF;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String south(String command) {
        Game.status = HomeStatus.STAIRCASE_DOWN;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String inspect(String item) {
        if (item.equals(STAIRS))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(STAIRS))
                return getThingDescription(STAIRS, DESCRIPTION);

        return INCORRECT;
    }

    @Override
    public String use(String item) {
        if (item.equals(NO_NAME)) {
            if (isLastMessageWasOfThink(STAIRS, DESCRIPTION) || isLastMessageWasOfThink(STAIRS, DISABLED))
                return useStairs();
        }

        if (item.equals(STAIRS))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(STAIRS))
                return useStairs();

        return INCORRECT;
    }

    private String useStairs() {
        if (isLastMessageWasOfThink(STAIRS, DESCRIPTION) ||
                isLastMessageWasOfThink(STAIRS, DISABLED)) {
            if (!getThingByName(STAIRS).isOpen) {
                getThingByName(STAIRS).isOpen = true;
                return getThingDescription(STAIRS, USING);
            } else return getThingDescription(STAIRS, ENABLED);
        }

        if (isSectionDescriptionWasLastMessage()) {
            if (!getThingByName(STAIRS).isOpen) {
                getThingByName(STAIRS).isOpen = true;

                return getThingDescription(STAIRS, USING);
            } else return getThingDescription(STAIRS, ENABLED);
        }

        LOG.warn("Такой команды нет!");
        return INCORRECT;
    }
}
