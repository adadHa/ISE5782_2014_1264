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
     * Constructor for PointLight
     * @param intensity
     * @param position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }
    
    /**
     * Setter method for kC
     * @param kC
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter method for kL
     * @param kL
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter method for kQ
     * @param kQ
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        Color iL = intensity.reduce(getDistanceByK(p));
        return iL;
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(position);
    }

    protected double getDistanceByK(Point p){
        double distance = p.distance(position);
        return  kC +
                kL*distance +
                kQ*distance*distance;
    }
}
