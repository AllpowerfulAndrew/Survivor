package survivor.model.gameElements.things;

import survivor.model.gameElements.items.Item;

import java.util.ArrayList;

public class CommonThing extends Thing {

    public CommonThing(String name, ArrayList<Item> items, boolean isOpen, String file) {
        super(name, items, isOpen, file);
    }
}
