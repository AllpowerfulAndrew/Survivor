package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameElements.items.ContainableItem;
import survivor.model.gameElements.items.TakeableItem;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameStatus.HomeStatus;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

import java.util.ArrayList;

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

        addToAllDescriptions(SECTION, sectionDescriptions);
        addToAllDescriptions(CUPBOARD, getAllDescriptionsOfThink(CUPBOARD));
        addToAllDescriptions(STOVE, getAllDescriptionsOfThink(STOVE));
        addToAllDescriptions(FRIDGE, getAllDescriptionsOfThink(FRIDGE));
        addToAllDescriptions(WINDOW, getAllDescriptionsOfThink(WINDOW));
    }

    public String otherInteraction(String[] command) {
        if (command.length == ONE) return oneCommand(command[FIRST]);
        if (command.length == TWO) return twoCommand(command);

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String oneCommand(String command) {
        if ((command.equals(EAST) || command.equals(EAST_S)) && Game.status.equals(HomeStatus.KITCHEN))
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.HALLWAY;
                return Game.mainInteraction(new String[]{command});
            }

        if (command.equals(NORTH) || command.equals(NORTH_S) || command.equals(WEST) || command.equals(WEST_S) ||
                command.equals(SOUTH) || command.equals(SOUTH_S))
            if (isSectionDescriptionWasLastMessage())
                return getSectionDescription(NO_WAY);

        if (command.equals(OPEN) || command.equals(OPEN_S)) {
            if (isLastMessageWasOfThink(FRIDGE, DESCRIPTION))
                return getThingDescription(FRIDGE, IS_EMPTY);
            if (isLastMessageWasOfThink(CUPBOARD, DESCRIPTION))
                return openCupboard();
        }

        if (command.equals(TAKE) || command.equals(TAKE_S))
            if (isLastMessageWasOfItemOfThink(CUPBOARD, KNIFE, DESCRIPTION))
                return takeTheKnife();

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String twoCommand(String[] command) {
        if (command[FIRST].equals(INSPECT) || command[FIRST].equals(INSPECT_S)) {
            if (command[SECOND].equals(KNIFE))
                if (isLastMessageWasOfThink(CUPBOARD, CUPBOARD_WITH_KNIFE) || isLastMessageWasOfThink(CUPBOARD, TAKING_KNIFE))
                    return getItemDescriptionOfThing(CUPBOARD, KNIFE, DESCRIPTION);

            if (command[SECOND].equals(CUPBOARD))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD))
                    return getThingDescription(CUPBOARD, DESCRIPTION);

            if (command[SECOND].equals(FRIDGE))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(FRIDGE))
                    return getThingDescription(FRIDGE, DESCRIPTION);

            if (command[SECOND].equals(STOVE_W))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(STOVE))
                    return getThingDescription(STOVE, DESCRIPTION);

            if (command[SECOND].equals(WINDOW))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(WINDOW))
                    return getThingDescription(WINDOW, DESCRIPTION);
        }

        if (command[FIRST].equals(OPEN) || command[FIRST].equals(OPEN_S)) {
            if (command[SECOND].equals(CUPBOARD))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD))
                    return openCupboard();

            if (command[SECOND].equals(FRIDGE))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(FRIDGE))
                    return getThingDescription(FRIDGE, IS_EMPTY);
        }

        if (command[FIRST].equals(TAKE) || command[FIRST].equals(TAKE_S))
            if (!isSectionDescriptionWasLastMessage() && command[SECOND].equals(KNIFE))
                return takeTheKnife();

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
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
        return Game.incorrect;
    }
}
