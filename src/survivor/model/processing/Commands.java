package survivor.model.processing;

public abstract class Commands {
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRTH = 2;

    public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;

    // movement
    public static final String NORTH = "север";
    public static final String NORTH_S = "с";
    public static final String SOUTH = "юг";
    public static final String SOUTH_S = "ю";
    public static final String WEST = "запад";
    public static final String WEST_S = "з";
    public static final String EAST = "восток";
    public static final String EAST_S = "в";

    // interaction
    public static final String OPEN = "открыть";
    public static final String OPEN_S = "отк";
    public static final String USE = "использовать";
    public static final String USE_S = "исп";
    public static final String INSPECT = "изучить";
    public static final String INSPECT_S = "из";
    public static final String TAKE = "взять";
    public static final String TAKE_S = "вз";
    public static final String DROP = "выбросить";
    public static final String DROP_S = "выб";

    // single
    public static final String LOOK_AROUND = "осмотреться";
    public static final String LOOK_AROUND_S = "осм";
    public static final String INVENTORY = "инвентарь";
    public static final String INVENTORY_S = "инв";
    public static final String HELP = "помощь";
    public static final String HELP_S = "пом";

    String[] ALL = {
            "с", "север", "ю", "юг", "з", "запад", "в", "восток",
            "осм", "осмотерться", "из", "изучить", "вз", "взять", "выб", "выбросить",
            "исп", "использовать", "инв", "инвентарь", "помощь"
    };
}