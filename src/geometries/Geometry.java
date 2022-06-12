package geometries;
import primitives.*;

/**
 * this abstract class represent any kind of geometry, all geometries extends it
 */
public abstract class Geometry extends Intersectable {

    /**
     * abstract method, every geometry implements it
     * @param p
     * @return a normal (perpendicular) vector to the geometry in point p, if there
     */
    public abstract Vector getNormal(Point p);

    protected Color emission = Color.BLACK;

    private Material material = new Material();

    /**
     * getter method for material field
     * @return
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * setter method for material field
     * @param material
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

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
