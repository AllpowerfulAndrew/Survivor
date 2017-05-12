package survivor.model.processing;

public interface Commands {
    int FIRST = 0;
    int SECOND = 1;
    int THIRTH = 2;

    int ONE = 1;
    int TWO = 2;
    int THREE = 3;

    // movement
    String NORTH = "север";
    String NORTH_S = "с";
    String SOUTH = "юг";
    String SOUTH_S = "ю";
    String WEST = "запад";
    String WEST_S = "з";
    String EAST = "восток";
    String EAST_S = "в";

    // interaction
    String OPEN = "открыть";
    String OPEN_S = "отк";
    String USE = "использовать";
    String USE_S = "исп";
    String INSPECT = "изучить";
    String INSPECT_S = "из";
    String TAKE = "взять";
    String TAKE_S = "вз";
    String DROP = "выбросить";
    String DROP_S = "выб";

    // single
    String LOOK_AROUND = "осмотреться";
    String LOOK_AROUND_S = "осм";
    String INVENTORY = "инвентарь";
    String INVENTORY_S = "инв";
    String HELP = "помощь";
    String HELP_S = "пом";

    String[] ALL = {
            "с", "север", "ю", "юг", "з", "запад", "в", "восток",
            "осм", "осмотерться", "из", "изучить", "вз", "взять", "выб", "выбросить",
            "исп", "использовать", "инв", "инвентарь", "помощь"
    };
}
