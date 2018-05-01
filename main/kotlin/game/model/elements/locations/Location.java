package game.model.elements.locations;

import game.model.basics.Game;
import game.model.constants.HomeStatus;
import game.model.constants.StoryStatus;
import game.model.elements.sections.Section;

import java.util.ArrayList;

import static game.model.constants.Messages.INCORRECT;

public abstract class Location {
    public String name;
    public ArrayList<Section> sections;
    public final ArrayList<String> sectionsId = new ArrayList<>();

    public Location(String name) {
        this.name = name;
        sections = new ArrayList<>();
    }

    public String interaction(String[] command) {
        for (int i = 0; i < sections.size(); i++) {
            if (Game.status.equals(sections.get(i).SECTION_NAME)) {
                return sections.get(i).interaction(command);
            }
        }

        if (Game.status.equals(StoryStatus.INTRO)) {
            for (int i = 0; i < sections.size(); i++) {
                if (sections.get(i).SECTION_NAME.equals(HomeStatus.BEDROOM)) {
                    return sections.get(i).interaction(command);
                }
            }
        }

        return INCORRECT;
    }

}