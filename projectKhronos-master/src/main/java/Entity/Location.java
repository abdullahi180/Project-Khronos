package Entity;


public class Location {
    private float x;
    private float y;
    private boolean isBoundary;

    public Location(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        //System.out.println("equals called! to x = "+x +"  y = "+ y);

        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Location)) {
            return false;
        }
        Location loc = (Location) obj;

        //  System.out.println("obj x = "+loc.x +"  y = "+ loc.y +"\n");

        if ((loc.x != x) || (loc.y != y)) {
            return false;
        }
        return true;
    }


    public Float getY() {
        return y;
    }

    public Float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setBoundary(boolean boundary) {
        isBoundary = boundary;
    }

    public boolean isBoundary() {
        return isBoundary;
    }

    public boolean checkValidity() {

        if (x > 0 && x <= 10 && y > 0 && y <= 10) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        // TODO will probably break
        return Integer.parseInt(x + "-" + y);
    }
}
