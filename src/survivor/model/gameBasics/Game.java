package survivor.model.gameBasics;

import org.apache.log4j.Logger;
import survivor.model.gameConstants.GameStatus;
import survivor.model.gameConstants.HomeStatus;
import survivor.model.gameConstants.StoryStatus;
import survivor.model.gameElements.Elements;
import survivor.model.gameElements.locations.Home;
import survivor.model.gameElements.locations.Location;
import survivor.model.gameElements.locations.Street;
import survivor.model.processing.Commands;
import survivor.model.processing.Files;
import survivor.model.processing.Reader;

import java.util.ArrayList;

import static survivor.model.gameConstants.Messages.INCORRECT;

public abstract class Game implements Commands, Elements {
    public static boolean isTimingOn;
    public static int difficulty = 1;
    private static final Logger LOG = Logger.getLogger(Game.class);
    public static String status = "Menu";
    private static String help = "";
    public static String message;
    public static String lastMessage;
    public static String actualSectionDescription = "";
    public static ArrayList<String> environment;
    private static ArrayList<Location> locations;
    private static ArrayList<String> events;

    static {
        locations = new ArrayList<>();
        locations.add(new Home());
        locations.add(new Street());
    }

    public static String mainInteraction(String[] command) {
        LOG.info("Актуальный статус игры: " + Game.status);
        if ((!Game.status.equals(StoryStatus.INTRO) && !Game.status.equals(GameStatus.MENU)) && command.length == 1)
            return oneCommand(command[FIRST]);
        else if (Game.status.equals(GameStatus.MENU)) {
            Game.status = StoryStatus.INTRO;
            LOG.info("Статус Menu поменялся на " + Game.status);
            return Story.getIntro();
        }

        return sectionInteraction(command);
    }

    private static String sectionInteraction(String[] command) {
        LOG.info("Приступаем к поиску нужной локации для взаимодействия. Статус игры: " + status);
        for (int i = 0; i < locations.size(); i++)
            if (locations.get(i).sectionsId.contains(status)) {
                LOG.info("Локация найдена. Отправлем запрос в локацию " + locations.get(i).name);
                return locations.get(i).interaction(command);
            }

        if (status.equals(StoryStatus.INTRO)) {
            for (int i = 0; i < locations.size(); i++) {
                if (locations.get(i).sectionsId.contains(HomeStatus.BEDROOM)) {
                    LOG.info("Локация найдена. Отправлем запрос в секцию " + locations.get(i).name);
                    return locations.get(i).interaction(command);
                }
            }
        }

        LOG.warn("Локация не найдена!");
        return INCORRECT;
    }

    private static String oneCommand(String command) {
        LOG.info("Приступаем к выполнению одиночной команды");
        if (command.equals(INVENTORY) || command.equals(INVENTORY_S)) return Player.showInventory();
        if (command.equals(HELP) || command.equals(HELP_S)) return Game.getHelp();
        if (command.equals(LOOK_AROUND) || command.equals(LOOK_AROUND_S)) return Game.actualSectionDescription;

        return Game.sectionInteraction(new String[]{command});
    }

    public static String getHelp() {
        if (help.equals("")) {
            ArrayList<String> list = Reader.readLocationFromFile(Files.HELP);

            for (int i = 0; i < list.size(); i++) {
                help = help + list.get(i) + "\n";
            }

            return help;
        } else return help;
    }

    public static String getEventMessage(int num) {
        if (events == null) setEvents();
        return events.get(num);
    }

    private static void setEvents() {
        events = Reader.readLocationFromFile(Files.EVENTS);
    }
}
