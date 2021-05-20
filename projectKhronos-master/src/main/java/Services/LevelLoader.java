package Services;

import java.util.Map;


public class LevelLoader {
    private Map<String, Level> levels;
    private String lvlKey;


    public LevelLoader(String key) {
        // ini vars
        lvlKey = key;
    }

    //for immediate editing levels will be de/serialised later
    private void GenerateLevels() {
        Level lvl = null;
        {
            // YOU DEFINE LEVEL HERE !!! needed for immediate editing


        }
        String key = "Level-1";
        levels.put(key, lvl);

    }


    public Level get() {
        return levels.get(lvlKey);
    }


    // worry about this later
    public void update(Level level) {//?

    }

    public void save() {//?

    }

    private void loadFromSave() {

    }

}
