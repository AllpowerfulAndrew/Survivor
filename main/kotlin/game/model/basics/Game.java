package game.model.basics;

import game.model.constants.GameStatus;
import game.model.constants.HomeStatus;
import game.model.constants.StoryStatus;
import game.model.elements.locations.Home;
import game.model.elements.locations.Location;
import game.model.elements.locations.Street;
import game.model.processing.Files;
import game.model.processing.Reader;

import java.util.ArrayList;

import static game.model.constants.Messages.INCORRECT;
import static game.model.processing.Commands.*;

public abstract class Game {
    public static boolean isTimingOn;
    public static int difficulty = 1;
    public static String status = "Menu";
    private static String help = "";
    public static String lastMessage;
    public static String actualSectionDescription = "";
    private static ArrayList<Location> locations;
    private static ArrayList<String> events;

    static {
        locations = new ArrayList<>();
        locations.add(new Home());
        locations.add(new Street());
    }

    public static String mainInteraction(String[] command) {
        if ((!Game.status.equals(StoryStatus.INTRO) && !Game.status.equals(GameStatus.MENU)) && command.length == 1)
            return oneCommand(command[FIRST]);
        else if (Game.status.equals(GameStatus.MENU)) {
            Game.status = StoryStatus.INTRO;
            return Story.getIntro();
        }

        return sectionInteraction(command);
    }

    private static String sectionInteraction(String[] command) {
        for (int i = 0; i < locations.size(); i++)
            if (locations.get(i).sectionsId.contains(status)) {
                return locations.get(i).interaction(command);
            }

        if (status.equals(StoryStatus.INTRO)) {
            for (int i = 0; i < locations.size(); i++) {
                if (locations.get(i).sectionsId.contains(HomeStatus.BEDROOM)) {
                    return locations.get(i).interaction(command);
                }
            }
        }

        return INCORRECT;
    }

    private static String oneCommand(String command) {
        if (command.equals(INVENTORY) || command.equals(INVENTORY_S)) return Player.showInventory();
        if (command.equals(HELP) || command.equals(HELP_S)) return Game.getHelp();
        if (command.equals(LOOK_AROUND) || command.equals(LOOK_AROUND_S)) return Game.actualSectionDescription;

        return Game.sectionInteraction(new String[]{command});
    }

    public static String getHelp() {
        if (help.equals("")) {
            ArrayList<String> list = Reader.readLocationFromFile(Files.HELP);

            for (int i = 0; i < list.size(); i++) {
                help += list.get(i) + "\n";
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