package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * this class represent a Directional Light, the light has a direction vector, and near to the vector the intensity is less
 */
public class DirectionalLight extends Light implements LightSource {
    private final Vector direction;

    /**
     * Constructor for DirectionalLight
     * @param intensity the color of the light
     * @param direction the direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * getter for Intensity
     * @param p
     * @return the color of the light on the point p
     */
    @Override
    public Color getIntensity(Point p) {
        return intensity;
    }

    /**
     * getter for the direction of the light (if exist)
     * @param p
     * @return the direction of the light in the point p
     */
    @Override
    public Vector getL(Point p) {
        return direction;
    }

    /**
     *
     * @param point
     * @return the distance between the light source and between the point, ask Har`el
     */
    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }

}
