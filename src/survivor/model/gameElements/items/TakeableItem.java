package survivor.model.gameElements.items;

public class TakeableItem extends Item {
    public final double weight;
    public boolean isTakeble;

    public TakeableItem(String name, double weight, boolean isTakeble, String file) {
        super(name, file);
        this.weight = weight;
        this.isTakeble = isTakeble;
    }
}
