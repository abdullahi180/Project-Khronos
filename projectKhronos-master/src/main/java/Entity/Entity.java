package Entity;

import org.lwjgl.opengl.GL11;

public abstract class Entity implements Comparable<Entity> {
    public static int[] globalDifferential;

    protected boolean hasCollisions;
    protected boolean hasAnimation;
    protected boolean differentialCords;
    protected int renderingOverlapLayer;
    protected Texture texture;
    private int[] sizeDimensions;
    private Location globalLocation;
    private float rotationAngle;


    public Entity(int height, int width, Location globalLocation) {
        sizeDimensions = new int[]{height, width};
        this.globalLocation = globalLocation;

    }

    @Override
    public int compareTo(Entity e) {
        // Migrated from Collisions
        return 0;
    }

    public void draw() {
        //GL11

        int width = sizeDimensions[0];
        int height = sizeDimensions[1];

        GL11.glPushMatrix();
        moveToGlobalLoc();
        rotate();
        //texture.bind();
        GL11.glBegin(GL11.GL_QUADS);

        //glTexCoord2f(0,1);
        GL11.glVertex2f(-width / 2f, -height / 2f);

        //glTexCoord2f(1,1);
        GL11.glVertex2f(width / 2f, -height / 2f);

        //glTexCoord2f(1,0);
        GL11.glVertex2f(width / 2f, height / 2f);

        //glTexCoord2f(0,0);
        GL11.glVertex2f(-width / 2f, height / 2f);
        GL11.glEnd();

        GL11.glPopMatrix();

    }


    protected void moveToGlobalLoc() {
        float x = globalLocation.getX();
        float y = globalLocation.getY();
        GL11.glTranslated(x, y, 0);
    }

    protected void rotate() {
        GL11.glRotatef(rotationAngle, 0, 0, 1);

    }


    public void look(Location loc) {

    }

    public void setRotation(float rotation) {
        rotationAngle = rotation;
    }

    public void moveBy(float x, float y) {
        globalLocation.setX(globalLocation.getX() + x);
        globalLocation.setY(globalLocation.getY() + y);
    }

    public void moveTo(float x, float y) {

    }

    public Location getLocation() {
        return null;

    }


}
