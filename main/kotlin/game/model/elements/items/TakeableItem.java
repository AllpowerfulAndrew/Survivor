package game.model.elements.items;

public class TakeableItem extends Item {
    public final double weight;
    public boolean isTakeable;

    public TakeableItem(String name, double weight, boolean isTakeable, String file) {
        super(name, file);
        this.weight = weight;
        this.isTakeable = isTakeable;
    }
}