package survivor.model.gameElements.locations;

import org.apache.log4j.Logger;
import survivor.model.gameElements.sections.Section;
import survivor.model.gameStatus.HomeStatus;
import survivor.model.gameStatus.StoryStatus;
import survivor.model.gameBasics.Game;

import java.util.ArrayList;

public abstract class Location {
    private static final Logger LOG = Logger.getLogger(Location.class);
    public String name;
    public ArrayList<Section> sections;
    public final ArrayList<String> sectionsId = new ArrayList<>();

    public Location(String name) {
        this.name = name;
        sections = new ArrayList<>();
    }

    public String interaction(String[] command) {
        LOG.info("Приступаем к поиску нужной секции. Статус игры: " + Game.status);
        for (int i = 0; i < sections.size(); i++) {
            if (Game.status.equals(sections.get(i).SECTION)) {
                LOG.info("Отправлем запрос в секцию " + sections.get(i).SECTION);
                return sections.get(i).interaction(command);
            }
        }

        if (Game.status.equals(StoryStatus.INTRO)) {
            for (int i = 0; i < sections.size(); i++) {
                if (sections.get(i).SECTION.equals(HomeStatus.BEDROOM)) {
                    LOG.info("Отправлем запрос в секцию " + sections.get(i).SECTION);
                    return sections.get(i).interaction(command);
                }
            }
        }

        LOG.warn("Секция не обнаружена!");
        return Game.incorrect;
    }

}
