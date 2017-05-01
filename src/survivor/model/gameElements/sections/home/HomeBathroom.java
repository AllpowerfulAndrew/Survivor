package survivor.model.gameElements.sections.home;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameBasics.Player;
import survivor.model.gameElements.Elements;
import survivor.model.gameElements.items.CommonItem;
import survivor.model.gameElements.items.Item;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameElements.things.CommonThing;
import survivor.model.gameStatus.HomeStatus;
import survivor.model.processing.*;

import java.util.ArrayList;

public class HomeBathroom extends Section implements Elements, Commands {
    private static final Logger LOG = Logger.getLogger(HomeBathroom.class);

    private final int CUPBOARD_OPENING = 1;

    private final int IS_CLOSED = 1;
    private final int AID_OPENING = 2;
    private final int WITH_KEY = 3;
    private final int TAKING_KEY = 4;
    private final int EMPTY = 5;

    public HomeBathroom() {
        super(HomeStatus.BATHROOM, true, true);
        sectionDescriptions = Reader.readLocationFromFile(Files.HOME_BATHROOM);

        ArrayList<Item> items = new ArrayList<>();
        items.add(new CommonItem(KEY, .2, Files.BATHROOM_AID_KIT_KEY));
        sectionThings.add(new CommonThing(AID_KIT, items, false, Files.BATHROOM_AID_KIT));
        sectionThings.add(new CommonThing(CUPBOARD_S, new ArrayList<>(), true, Files.BATHROOM_CUPBOARD));

        addToAllDescriptions(SECTION, sectionDescriptions);
        addToAllDescriptions(AID_KIT, getAllDescriptionsOfThink(AID_KIT));
        addToAllDescriptions(CUPBOARD_S, getAllDescriptionsOfThink(CUPBOARD_S));
        addToAllDescriptions(KEY, getAllDescriptionsOfThinkItem(AID_KIT, KEY));
    }

    public String otherInteraction(String[] command) {
        if (command.length == ONE) return oneCommand(command[FIRST]);
        if (command.length == TWO) return twoCommand(command);

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String oneCommand(String command) {
        if ((command.equals(SOUTH) || command.equals(SOUTH_S)) && Game.status.equals(HomeStatus.BATHROOM))
            if (isSectionDescriptionWasLastMessage()) {
                Game.status = HomeStatus.HALLWAY;
                return Game.mainInteraction(new String[]{command});
            }

        if (command.equals(NORTH) || command.equals(NORTH_S) || command.equals(WEST) || command.equals(WEST_S) ||
                command.equals(EAST) || command.equals(EAST_S))
            if (isSectionDescriptionWasLastMessage())
                return getSectionDescription(NO_WAY);

        if (command.equals(OPEN) || command.equals(OPEN_S)) {
            if (isLastMessageWasOfThink(AID_KIT, DESCRIPTION))
                return openAidKid();

            if (isLastMessageWasOfThink(CUPBOARD_S, DESCRIPTION))
                return openCupboard();
        }

        if (command.equals(TAKE) || command.equals(TAKE_S))
            if (isLastMessageWasOfItemOfThink(AID_KIT, KEY, DESCRIPTION))
                return takeKey();

        LOG.warn("Такой команды нет!");
        return Game.incorrect;
    }

    private String twoCommand(String[] command) {
        if (command[FIRST].equals(INSPECT) || command[FIRST].equals(INSPECT_S)) {
            if (command[SECOND].equals(AID_KIT_W))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(AID_KIT))
                    return getThingDescription(AID_KIT, DESCRIPTION);

            if (command[SECOND].equals(CUPBOARD_S))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD_S))
                    return getThingDescription(CUPBOARD_S, DESCRIPTION);

            if (command[SECOND].equals(KEY))
                if (isLastMessageWasOfThink(AID_KIT, AID_OPENING) || isLastMessageWasOfThink(AID_KIT, WITH_KEY))
                    return getItemDescriptionOfThing(AID_KIT, KEY, DESCRIPTION);
        }

        if (command[FIRST].equals(OPEN) || command[FIRST].equals(OPEN_S)) {
            if (command[SECOND].equals(AID_KIT_W))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(AID_KIT))
                    return openAidKid();

            if (command[SECOND].equals(CUPBOARD_S))
                if (isSectionDescriptionWasLastMessage() || isLastMessageWasDescriptionOf(CUPBOARD_S))
                    return openCupboard();
        }

        if (command[FIRST].equals(TAKE) || command[FIRST].equals(TAKE_S))
            if (command[SECOND].equals(KEY))
                return takeKey();

        if ((command[FIRST].equals(USE) || command[FIRST].equals(USE_S)) && command[SECOND].equals(KNIFE))
            if (isLastMessageWasOfThink(AID_KIT, DESCRIPTION) || isLastMessageWasOfThink(AID_KIT, IS_CLOSED))
                if (Player.doesHaveItem(KNIFE)) {
                    Player.deleteItem(KNIFE);
                    getThingByName(AID_KIT).isOpen = true;
                    return getThingDescription(AID_KIT, AID_OPENING);
                }

        LOG.warn("Ошибочная команда!");
        return Game.incorrect;
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
        LOG.info("Проверяем, было ли последним на экране нужное нам описание");
        if (isLastMessageWasOfThink(AID_KIT, AID_OPENING) ||
                isLastMessageWasOfThink(AID_KIT, WITH_KEY) ||
                isLastMessageWasOfItemOfThink(AID_KIT, KEY, DESCRIPTION)) {
            LOG.info("Проверяем, есть ли ключи в аптечке");
            if (isItemOfThingExist(AID_KIT, KEY)) {
                takeItemOfThing(AID_KIT, KEY);
                return getThingDescription(AID_KIT, TAKING_KEY);
            }
            LOG.info("Ключи не найдены!");
        }

        LOG.warn("Ошибочная команда!");
        return Game.incorrect;
    }
}