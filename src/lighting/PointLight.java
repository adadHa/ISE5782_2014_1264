package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This class represents a pointlight - like a bulb.
 */
public class PointLight extends Light implements LightSource{
    /**
     * The position point of the pointlight
     */
    private final Point position;

    private double kC = 1;
    private double kL = 0;
    private double kQ = 0;

    /**
     * Setter method for kC
     * @param kC
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter method for kL
     * @param kL
     */
    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter method for kQ
     * @param kQ
     */
    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Constructor for PointLight
     * @param intensity
     * @param position
     * @param kC
     * @param kL
     * @param kQ
     */
    public PointLight(Color intensity, Point position, double kC, double kL, double kQ) {
        super(intensity);
        this.position = position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }

    @Override
    public Color getIntensity(Point p) {
        Color iL = intensity.reduce(kC +
                                kL*p.distanceSquared(position) +
                                kQ*p.distance(position));
        return iL;
    }

    @Override
    public Vector getL(Point p) {
        return null;
    }
}
