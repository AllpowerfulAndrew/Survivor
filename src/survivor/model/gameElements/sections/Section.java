package survivor.model.gameElements.sections;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.gameBasics.Player;
import survivor.model.gameConstants.HomeStatus;
import survivor.model.gameConstants.StoryStatus;
import survivor.model.gameElements.Elements;
import survivor.model.gameElements.items.ContainableItem;
import survivor.model.gameElements.items.Item;
import survivor.model.gameElements.items.TakeableItem;
import survivor.model.processing.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static survivor.model.gameBasics.Temperature.setTemperature;
import static survivor.model.gameBasics.Time.increaseTime;
import static survivor.model.gameConstants.Messages.INCORRECT;

public abstract class Section implements Commands, Elements {
    private static final Logger LOG = Logger.getLogger(Section.class);

    protected final int ENTER = 0;
    protected final int AROUND = 1;
    protected final int NO_WAY = 2;

    protected final int DESCRIPTION = 0;

    public final String SECTION_NAME;
    private final boolean IS_ROOM;
    private final boolean INHABITED;
    private List<TakeableItem> sectionItems;
    private List<TakeableItem> droppedItems;
    protected List<ContainableItem> sectionThings;
    protected ArrayList<String> sectionDescriptions;
    private final HashMap<String, ArrayList<String>> allDescriptions = new HashMap<>();

    public Section(String name, boolean isRoom, boolean inhabited) {
        sectionThings = new ArrayList<>();
        sectionItems = new ArrayList<>();
        droppedItems = new ArrayList<>();
        sectionDescriptions = new ArrayList<>();
        SECTION_NAME = name;
        IS_ROOM = isRoom;
        INHABITED = inhabited;
    }

    public Item getSectionThing(String name) {
        for (Item i : sectionThings) {
            if (i.name.equals(name)) {
                return i;
            }
        }

        throw new IllegalArgumentException();
    }

    public ContainableItem getThingByName(String name) {
        LOG.info("Получаем вещь по имени");
        for (int i = 0; i < sectionThings.size(); i++) {
            if (sectionThings.get(i).name.equals(name)) return sectionThings.get(i);
        }

        LOG.warn("Вещь не найдена!");
        throw new IllegalArgumentException();
    }

    public Item getThingItemByName(String thing, String item) {
        for (int i = 0; i < sectionThings.size(); i++)
            if (sectionThings.get(i).name.equals(thing))
                for (int j = 0; j < sectionThings.get(i).items.size(); j++)
                    if (sectionThings.get(i).items.get(j).name.equals(item))
                        return sectionThings.get(i).items.get(j);

        LOG.warn("Вещь не найдена!");
        throw new IllegalArgumentException();
    }

    public Item getSectionItemByName(String name) {
        LOG.info("Получаем предмет по имени");
        for (Item i : sectionItems)
            if (i.name.equals(name)) return i;

        throw new IllegalArgumentException();
    }

    public Item getDroppedItemByName(String name) {
        LOG.info("Получаем предмет по имени");
        for (Item i : droppedItems)
            if (i.name.equals(name)) return i;

        throw new IllegalArgumentException();
    }

    public String getSectionDescription(int num) {
        LOG.info("Получаем " + num + " описание секции");
        String description = sectionDescriptions.get(num);

        if (!droppedItems.isEmpty()) {
            LOG.info("Добавляем к описанию выброшенные предметы");
            description += showDroppedItem();
            Game.actualSectionDescription = sectionDescriptions.get(1) + showDroppedItem();
        } else Game.actualSectionDescription = sectionDescriptions.get(1);

        return description;
    }

    public String getThingDescription(String name, int num) {
        for (int i = 0; i < sectionThings.size(); i++)
            if (sectionThings.get(i).name.equals(name))
                return sectionThings.get(i).getDescription(num);

        throw new IllegalArgumentException();
    }

    public String getSectionItemDescription(String name, int num) {
        for (Item i : sectionItems) {
            if (i.name.equals(name)) {
                return i.getDescription(num);
            }
        }

        throw new IllegalArgumentException();
    }

    public String getDroppedItemDescription(String name, int num) {
        for (Item i : droppedItems)
            if (i.name.equals(name))
                return i.getDescription(num);

        throw new IllegalArgumentException();
    }

    public String getItemDescriptionOfThing(String thing, String item, int num) {
        return getThingByName(thing).getItemDescription(item, num);
    }

    public ArrayList<String> getAllDescriptionsOfThink(String name) {
        return getThingByName(name).getDescriptionsList();
    }

    public ArrayList<String> getAllDescriptionsOfItem(String name) {
        return getSectionItemByName(name).getDescriptionsList();
    }

    public ArrayList<String> getAllDescriptionsOfThinkItem(String think, String item) {
        return getThingItemByName(think, item).getDescriptionsList();
    }

    public void addSectionItem(TakeableItem item) {
        sectionItems.add(item);
    }

    public void addToAllDescriptions(String name, ArrayList<String> descriptions) {
        allDescriptions.put(name, descriptions);
    }

    public void changeContaineableItem(ContainableItem thing) {
        for (Item t : sectionThings)
            if (t.name.equals(thing.name))
                if (!t.equals(thing)) {
                    sectionThings.remove(t);
                    sectionThings.add(thing);
                }
    }

    public Item takeSectionItem(String name) {
        for (Item i : sectionItems)
            if (i.name.equals(name)) {
                sectionItems.remove(i);
                return i;
            }

        throw new IllegalArgumentException();
    }

    public void takeItemOfThing(String thing, String item) {
        Player.putInInventory(getThingByName(thing).takeItem(item));
    }

    public String dropAnItem(String name) {
        if (Player.doesHaveItem(name)) {
            LOG.info("Выбрасываем предмет " + name);
            droppedItems.add(Player.dropAnItem(name));
            Game.actualSectionDescription = getSectionDescription(1);
            return "Вы выбросили " + name + ". (ESC)";
        }

        return "В инвентаре нет такого предмета. (ESC)";
    }

    public String pickUpItem(String name) {
        for (int i = 0; i < droppedItems.size(); i++) {
            if (droppedItems.get(i).name.equals(name)) {
                LOG.info("Забираем выброшенный предмет " + name + " в инвентарь");
                Player.putInInventory(droppedItems.get(i));
                droppedItems.remove(i);
                Game.actualSectionDescription = getSectionDescription(1);

                return "Вы забрали " + name + ". (ESC)";
            }
        }

        throw new IllegalArgumentException();
    }

    public String showDroppedItem() {
        String items = "";

        for (int i = 0; i < droppedItems.size(); i++) {
            if (i == droppedItems.size() - 1) items += droppedItems.get(i).name + ".";
            else items += droppedItems.get(i).name + ", ";
        }

        LOG.info("Возвращаем список выброшенных предметов");
        return "\n\nТут есть предметы, оставленные Вами: " + items;
    }

    public boolean isExistInDroppedItems(String name) {
        for (Item item : droppedItems) if (item.name.equals(name)) return true;

        LOG.warn("Предмет не найден!");
        return false;
    }

    public boolean isThingIsEmpty(String name) {
        return getThingByName(name).items.isEmpty();
    }

    public boolean isItemOfThingExist(String thing, String item) {
        return getThingByName(thing).ifItemExist(item);
    }

    public boolean isSectionDescriptionWasLastMessage() {
        LOG.info("Проверяем, не было ли описание секции последним сообщением");
        return (Game.lastMessage.equals(getSectionDescription(ENTER)) ||
                Game.lastMessage.equals(getSectionDescription(AROUND)) ||
                isLastMessageWasDescriptionOf(SECTION_NAME));
    }

    public boolean isLastMessageWasInventory() {
        return Game.lastMessage.equals(Player.showInventory());
    }

    public boolean isLastMessageWasDescriptionOf(String name) {
        LOG.info("Проверяем, было ли последнее собщение одним из описаний предмета");
        return allDescriptions.get(name).contains(Game.lastMessage);
    }

    public boolean isLastMessageWasOfThink(String name, int num) {
        return Game.lastMessage.equals(getThingDescription(name, num));
    }

    public boolean isLastMessageWasOfSectionItem(String name, int num) {
        return Game.lastMessage.equals(getSectionItemDescription(name, num));
    }

    public boolean isLastMessageWasOfDroppedItem(String name, int num) {
        return Game.lastMessage.equals(getDroppedItemDescription(name, num));
    }

    public boolean isLastMessageWasOfItemOfThink(String think, String item, int num) {
        return Game.lastMessage.equals(getItemDescriptionOfThing(think, item, num));
    }

    public String interaction(String[] command) {
        LOG.info("Вошли в секцию " + SECTION_NAME);
        if (Game.status.equals(StoryStatus.INTRO)) {
            Player.location = HomeStatus.BEDROOM;
            Game.status = HomeStatus.BEDROOM;
            LOG.info("Статус игры изменён на " + Game.status);
            return getSectionDescription(ENTER);
        }

        if (!Player.location.equals(SECTION_NAME)) {
            Player.location = SECTION_NAME;
            setTemperature(IS_ROOM, INHABITED);
            increaseTime();
            return getSectionDescription(ENTER);
        }

        if (command.length == ONE) return oneCommand(command[FIRST]);
        if (command.length == TWO) return twoCommand(command);

        return INCORRECT;
    }

    private String oneCommand(String command) {
        if ((command.equals(NORTH) || command.equals(NORTH_S)) && Game.status.equals(SECTION_NAME) && isSectionDescriptionWasLastMessage())
            return north(command);

        if ((command.equals(SOUTH) || command.equals(SOUTH_S)) && Game.status.equals(SECTION_NAME) && isSectionDescriptionWasLastMessage())
            return south(command);

        if ((command.equals(WEST) || command.equals(WEST_S)) && Game.status.equals(SECTION_NAME) && isSectionDescriptionWasLastMessage())
            return west(command);

        if ((command.equals(EAST) || command.equals(EAST_S)) && Game.status.equals(SECTION_NAME) && isSectionDescriptionWasLastMessage())
            return east(command);

        if (command.equals(INSPECT) || command.equals(INVENTORY_S)) return inspect(NO_NAME);
        if (command.equals(OPEN) || command.equals(OPEN_S)) return open(NO_NAME);
        if (command.equals(USE) || command.equals(USE_S)) return use(NO_NAME);
        if (command.equals(TAKE) || command.equals(TAKE_S)) return take(NO_NAME);

        LOG.info("Неверная команда!");
        return INCORRECT;
    }

    private String twoCommand(String[] command) {
        if (isLastMessageWasInventory()) {
            LOG.info("Осматриваем предмет из инвенторя");
            if ((command[FIRST].equals(INSPECT) || command[FIRST].equals(INSPECT_S)) && Player.doesHaveItem(command[SECOND]))
                return Player.getItemDescription(command[SECOND]);
        }

        if (command[FIRST].equals(INSPECT) || command[FIRST].equals(INSPECT_S)) return inspect(command[SECOND]);
        if (command[FIRST].equals(OPEN) || command[FIRST].equals(OPEN_S)) return open(command[SECOND]);
        if (command[FIRST].equals(USE) || command[FIRST].equals(USE_S)) return use(command[SECOND]);

        if (command[FIRST].equals(TAKE) || command[FIRST].equals(TAKE_S)) {
            if (isSectionDescriptionWasLastMessage() && isExistInDroppedItems(command[SECOND]))
                return pickUpItem(command[SECOND]);

            return take(command[SECOND]);
        }

        if (command[FIRST].equals(DROP) || command[FIRST].equals(DROP_S)) {
            if (isLastMessageWasInventory() || isSectionDescriptionWasLastMessage())
                return dropAnItem(command[SECOND]);

            return drop(command[SECOND]);
        }

        LOG.info("Неверная команда!");
        return INCORRECT;
    }

    public String north(String command) {
        return getSectionDescription(NO_WAY);
    }

    public String south(String command) {
        return getSectionDescription(NO_WAY);
    }

    public String west(String command) {
        return getSectionDescription(NO_WAY);
    }

    public String east(String command) {
        return getSectionDescription(NO_WAY);
    }

    public String inspect(String item) {
        return INCORRECT;
    }

    public String open(String item) {
        return INCORRECT;
    }

    public String use(String item) {
        return INCORRECT;
    }

    public String take(String item) {
        return INCORRECT;
    }

    public String drop(String item) {
        return INCORRECT;
    }
}