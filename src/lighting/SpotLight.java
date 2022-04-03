package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

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

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
