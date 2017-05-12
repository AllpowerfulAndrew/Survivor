package survivor.model.gameElements.items;

import org.apache.log4j.Logger;
import survivor.model.gameBasics.Game;
import survivor.model.processing.Reader;

import java.util.ArrayList;

public class ContainableItem extends Item {
    private static final Logger LOG = Logger.getLogger(ContainableItem.class);

    public ArrayList<TakeableItem> items;
    public boolean isOpen;
    private ArrayList<String> description;

    public ContainableItem(String name, ArrayList<TakeableItem> items, boolean isOpen, String file) {
        super(name, file);
        this.items = items;
        this.isOpen = isOpen;
        description = Reader.readLocationFromFile(file);
    }

    public TakeableItem takeItem(String name) {
        TakeableItem item = null;

        LOG.info("Ищем предмет по имени");
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).name.equals(name)) {
                LOG.info("Нужный предмет найден. Забираем его из вещи к себе в инвентарь.");
                item = items.get(i);
                items.remove(i);
                break;
            }
        }

        return item;
    }

    public String getItemDescription(String name, int num) {
        LOG.info("Ищем предмет по имени");
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).name.equals(name)) {
                LOG.info("Возвращаем описание предмета");
                return items.get(i).getDescription(num);
            }
        }

        LOG.warn("Такого предмета нет!");
        return Game.incorrect;
    }

    public boolean ifItemExist(String name) {
        LOG.info("Проверяем, содержит ли вещь такой предмет");
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).name.equals(name)) {
                return true;
            }
        }

        LOG.warn("Такого предмета нет!");
        return false;
    }

    public String getDescription(int num) {
        return description.get(num);
    }

    public ArrayList<String> getDescriptionsList() {
        return description;
    }

}
