package game.model.elements.sections.home;

import game.model.basics.Game;
import game.model.constants.HomeStatus;
import game.model.elements.items.ContainableItem;
import game.model.elements.sections.Section;
import game.model.processing.Files;
import game.model.processing.Reader;

import java.util.ArrayList;

import static game.model.constants.Messages.INCORRECT;
import static game.model.elements.Elements.NO_NAME;
import static game.model.elements.Elements.STAIRS;

public class HomeStaircaseUp extends Section {
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
        if (!getThingByName(STAIRS).isOpen) return getThingDescription(STAIRS, DISABLED);

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
        if (item.equals(NO_NAME))
            if (isLastMessageWasOfThink(STAIRS, DESCRIPTION) || isLastMessageWasOfThink(STAIRS, DISABLED))
                return useStairs();

        if (item.equals(STAIRS))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(STAIRS))
                return useStairs();

        return INCORRECT;
    }

    private String useStairs() {
        if (isLastMessageWasOfThink(STAIRS, DESCRIPTION) ||
                isLastMessageWasOfThink(STAIRS, DISABLED))
            if (!getThingByName(STAIRS).isOpen) {
                getThingByName(STAIRS).isOpen = true;
                return getThingDescription(STAIRS, USING);
            } else
                return getThingDescription(STAIRS, ENABLED);


        if (isSectionDescriptionWasLastMessage()) {
            if (!getThingByName(STAIRS).isOpen) {
                getThingByName(STAIRS).isOpen = true;

                return getThingDescription(STAIRS, USING);
            } else
                return getThingDescription(STAIRS, ENABLED);
        }

        return INCORRECT;
    }
}