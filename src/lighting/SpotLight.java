package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * this class represent a SpotLight, that have color, starting point, and a direction vector to the light
 */
public class SpotLight extends PointLight{
    private final Vector direction;

    /**
     * Constructor for SpotLight
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    /**
     * get the intensity of the SpotLight in the point p
     * @param p
     * @return intensity
     */
    @Override
    public Color getIntensity(Point p) {
        Vector l = getL(p);
        double dirL = direction.dotProduct(l);
        if(dirL > 0){
            return intensity.scale(dirL).reduce(getDistanceByK(p));
        }
        else
            return intensity.scale(0);
    }

    /**
     * get the vector between the given point p and the point of the position of the SpotLight
     * @param p
     * @return vector(p-position of SpotLight)
     */
    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
