package geometries;
import primitives.*;

public abstract class Geometry extends Intersectable {
    public abstract Vector getNormal(Point p);
    protected Color emission = Color.BLACK;

    /**
     * getter method for emission field
     * @return emission of geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setter method for emission field
     * @param emission
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }
}
