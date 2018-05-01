package game.model.elements.sections.home;

import game.model.basics.Game;
import game.model.basics.Player;
import game.model.constants.HomeStatus;
import game.model.elements.items.ContainableItem;
import game.model.elements.items.TakeableItem;
import game.model.elements.sections.Section;
import game.model.processing.Files;
import game.model.processing.Reader;

import java.util.ArrayList;

import static game.model.constants.Messages.INCORRECT;
import static game.model.elements.Elements.*;

public class HomeBathroom extends Section {
    private final int CUPBOARD_OPENING = 1;

    private final int IS_CLOSED = 1;
    private final int AID_OPENING = 2;
    private final int WITH_KEY = 3;
    private final int TAKING_KEY = 4;
    private final int EMPTY = 5;

    public HomeBathroom() {
        super(HomeStatus.BATHROOM, true, true);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_BATHROOM);

        ArrayList<TakeableItem> items = new ArrayList<>();
        items.add(new TakeableItem(KEY, .2, true, Files.BATHROOM_AID_KIT_KEY));
        sectionThings.add(new ContainableItem(AID_KIT, items, false, Files.BATHROOM_AID_KIT));
        sectionThings.add(new ContainableItem(CUPBOARD_S, new ArrayList<>(), true, Files.BATHROOM_CUPBOARD));

        addToAllDescriptions(SECTION_NAME, sectionDescriptions);
        addToAllDescriptions(AID_KIT, getAllDescriptionsOfThink(AID_KIT));
        addToAllDescriptions(CUPBOARD_S, getAllDescriptionsOfThink(CUPBOARD_S));
        addToAllDescriptions(KEY, getAllDescriptionsOfThinkItem(AID_KIT, KEY));
    }

    @Override
    public String south(String command) {
        Game.status = HomeStatus.HALLWAY;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String inspect(String item) {
        if (item.equals(AID_KIT_W))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(AID_KIT))
                return getThingDescription(AID_KIT, DESCRIPTION);

        if (item.equals(CUPBOARD_S))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD_S))
                return getThingDescription(CUPBOARD_S, DESCRIPTION);

        if (item.equals(KEY))
            if (isLastMessageWasOfThink(AID_KIT, AID_OPENING) || isLastMessageWasOfThink(AID_KIT, WITH_KEY))
                return getItemDescriptionOfThing(AID_KIT, KEY, DESCRIPTION);

        return INCORRECT;
    }

    @Override
    public String open(String item) {
        if (item.equals(NO_NAME)) {
            if (isLastMessageWasOfThink(AID_KIT, DESCRIPTION))
                return openAidKid();

            if (isLastMessageWasOfThink(CUPBOARD_S, DESCRIPTION))
                return openCupboard();
        }

        if (item.equals(AID_KIT_W))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(AID_KIT))
                return openAidKid();

        if (item.equals(CUPBOARD_S))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD_S))
                return openCupboard();

        return INCORRECT;
    }

    @Override
    public String use(String item) {
        if (item.equals(KNIFE))
            if (isLastMessageWasOfThink(AID_KIT, DESCRIPTION) || isLastMessageWasOfThink(AID_KIT, IS_CLOSED))
                if (Player.doesHaveItem(KNIFE)) {
                    Player.deleteItem(KNIFE);
                    getThingByName(AID_KIT).isOpen = true;
                    return getThingDescription(AID_KIT, AID_OPENING);
                }

        return INCORRECT;
    }

    @Override
    public String take(String item) {
        if (item.equals(NO_NAME))
            if (isLastMessageWasOfItemOfThink(AID_KIT, KEY, DESCRIPTION))
                return takeKey();

        if (item.equals(KEY))
            return takeKey();

        return INCORRECT;
    }

    private String openAidKid() {
        if (getThingByName(AID_KIT).isOpen) {
            if (!isThingIsEmpty(AID_KIT)) return getThingDescription(AID_KIT, WITH_KEY);
            return getThingDescription(AID_KIT, EMPTY);
        } else return getThingDescription(AID_KIT, IS_CLOSED);
    }

    private String openCupboard() {
        return getThingDescription(CUPBOARD_S, CUPBOARD_OPENING);
    }

    private String takeKey() {
        if (isLastMessageWasOfThink(AID_KIT, AID_OPENING) ||
              isLastMessageWasOfThink(AID_KIT, WITH_KEY) ||
              isLastMessageWasOfItemOfThink(AID_KIT, KEY, DESCRIPTION)) {
            if (isItemOfThingExist(AID_KIT, KEY)) {
                takeItemOfThing(AID_KIT, KEY);
                return getThingDescription(AID_KIT, TAKING_KEY);
            }
        }

        return INCORRECT;
    }
}