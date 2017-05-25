package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameElements.items.ContainableItem;
import survivor.model.gameElements.items.TakeableItem;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameConstants.HomeStatus;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

import java.util.ArrayList;

import static survivor.model.gameConstants.Messages.INCORRECT;

public class HomeKitchen extends Section {
    private static final Logger LOG = Logger.getLogger(HomeKitchen.class);

    private final int IS_EMPTY = 1;

    private final int CUPBOARD_WITH_KNIFE = 1;
    private final int TAKING_KNIFE = 2;
    private final int CUPBOARD_EMPTY = 3;

    public HomeKitchen() {
        super(HomeStatus.KITCHEN, true, true);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_KITCHEN);

        ArrayList<TakeableItem> cupboardItems = new ArrayList<>();
        cupboardItems.add(new TakeableItem(KNIFE, .8, true, Files.KITCHEN_CUPBOARD_KNIFE));

        sectionThings.add(new ContainableItem(CUPBOARD, cupboardItems, true, Files.KITCHEN_CUPBOARD));
        sectionThings.add(new ContainableItem(STOVE, new ArrayList<>(), false, Files.KITCHEN_STOVE));
        sectionThings.add(new ContainableItem(FRIDGE, new ArrayList<>(), true, Files.KITCHEN_FRIDGE));
        sectionThings.add(new ContainableItem(WINDOW, new ArrayList<>(), false, Files.KITCHEN_WINDOW));

        addToAllDescriptions(SECTION_NAME, sectionDescriptions);
        addToAllDescriptions(CUPBOARD, getAllDescriptionsOfThink(CUPBOARD));
        addToAllDescriptions(STOVE, getAllDescriptionsOfThink(STOVE));
        addToAllDescriptions(FRIDGE, getAllDescriptionsOfThink(FRIDGE));
        addToAllDescriptions(WINDOW, getAllDescriptionsOfThink(WINDOW));
    }

    @Override
    public String east(String command) {
        Game.status = HomeStatus.HALLWAY;
        return Game.mainInteraction(new String[]{command});
    }

    @Override
    public String inspect(String item) {
        if (item.equals(KNIFE))
            if (isLastMessageWasOfThink(CUPBOARD, CUPBOARD_WITH_KNIFE) || isLastMessageWasOfThink(CUPBOARD, TAKING_KNIFE))
                return getItemDescriptionOfThing(CUPBOARD, KNIFE, DESCRIPTION);

        if (item.equals(CUPBOARD))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD))
                return getThingDescription(CUPBOARD, DESCRIPTION);

        if (item.equals(FRIDGE))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(FRIDGE))
                return getThingDescription(FRIDGE, DESCRIPTION);

        if (item.equals(STOVE_W))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(STOVE))
                return getThingDescription(STOVE, DESCRIPTION);

        if (item.equals(WINDOW))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(WINDOW))
                return getThingDescription(WINDOW, DESCRIPTION);

        return INCORRECT;
    }

    @Override
    public String open(String item) {
        if (item.equals(NO_NAME)) {
            if (isLastMessageWasOfThink(FRIDGE, DESCRIPTION))
                return getThingDescription(FRIDGE, IS_EMPTY);

            if (isLastMessageWasOfThink(CUPBOARD, DESCRIPTION))
                return openCupboard();
        }

        if (item.equals(CUPBOARD))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD))
                return openCupboard();

        if (item.equals(FRIDGE))
            if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(FRIDGE))
                return getThingDescription(FRIDGE, IS_EMPTY);

        return INCORRECT;
    }

    @Override
    public String take(String item) {
        if (item.equals(NO_NAME)) {
            if (isLastMessageWasOfItemOfThink(CUPBOARD, KNIFE, DESCRIPTION))
                return takeTheKnife();
        }

        if (!isSectionDescriptionWasLastMessage() && item.equals(KNIFE))
            return takeTheKnife();

        return INCORRECT;
    }

    private String openCupboard() {
        if (isThingIsEmpty(CUPBOARD)) {
            return getThingDescription(CUPBOARD, CUPBOARD_EMPTY);
        } else return getThingDescription(CUPBOARD, CUPBOARD_WITH_KNIFE);
    }

    private String takeTheKnife() {
        LOG.info("Проверяем было ли последним сообщением описание ножа");
        if (isLastMessageWasOfItemOfThink(CUPBOARD, KNIFE, DESCRIPTION) ||
                isLastMessageWasOfThink(CUPBOARD, CUPBOARD_WITH_KNIFE)) {
            LOG.info("Проверяем наличие ножа в шкафу");
            takeItemOfThing(CUPBOARD, KNIFE);
            return getThingDescription(CUPBOARD, TAKING_KNIFE);
        }

        LOG.warn("Ошибочная команда!");
        return INCORRECT;
    }
}
