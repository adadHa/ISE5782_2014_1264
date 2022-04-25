package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This interface represents a light source (with vector of light direction)
 */
public interface LightSource {
    /**
     * This method return the color intensity that the light source cause on point p
     * @param p
     * @return
     */
    public Color getIntensity(Point p);

    /**
     * This method return the direction of the light source (if exist)
     * @param p
     * @return
     */
    public Vector getL(Point p);

    /**
     * This method calc the distance from a point to the light source
     * @param point
     * @return the distance from a point to the light source
     */
    public double getDistance(Point point);
}
