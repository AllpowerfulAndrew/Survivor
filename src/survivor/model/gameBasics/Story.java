package survivor.model.gameBasics;

import survivor.model.processing.Files;
import survivor.model.processing.Reader;

import java.util.ArrayList;

public abstract class Story {
    private static String intro = "";

    public static String getIntro() {
        if (intro.equals("")) {
            ArrayList<String> list = Reader.readLocationFromFile(Files.INTRO);

            for (String line : list)
                intro += line + "\n";
        }

        return intro;
    }
}