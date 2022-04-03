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
     * @param kC
     * @param kL
     * @param kQ
     * @param direction
     */
    public SpotLight(Color intensity, Point position, double kC, double kL, double kQ, Vector direction) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity(p);
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
