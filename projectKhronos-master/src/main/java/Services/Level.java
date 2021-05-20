package Services;

import java.util.List;

public class Level implements Renderable {
    private List<Renderable> entities;

    public Level() {

    }

    // TODO define enemy object
    public List<Renderable> getEntities() {
        return entities;
    }

    // TODO define Texture Object
    public void bindTexture(Object Texture) {//?

    }

    public void render() {
        for (Renderable Entity : entities) {
            Entity.render();
        }
    }
}
