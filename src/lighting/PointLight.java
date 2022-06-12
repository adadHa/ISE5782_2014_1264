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

    /**
     * get the Intensity of the color of the PointLight in the point p (reduce by distance)
     * @param p
     * @return Intensity of the light in the point p (color)
     */
    @Override
    public Color getIntensity(Point p) {
        Color iL = intensity.reduce(getDistanceByK(p));
        return iL;
    }

    /**
     * get the vector from the point p to the place of the PointLight (position)
     * @param p
     * @return vector (p - position of the PointLight)
     */
    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    /**
     * get the distance between the given point and the position of the PointLight
     * @param point
     * @return distance between point and light position
     */
    @Override
    public double getDistance(Point point) {
        return point.distance(position);
    }

    /**
     * get the distance between the given point and the PointLight position, scaled by kC, kL, and kQ, to know the right color of the point
     * @param p
     * @return kC + kL*distance + kQ*distance^2
     */
    protected double getDistanceByK(Point p){
        double distance = p.distance(position);
        return  kC +
                kL*distance +
                kQ*distance*distance;
    }
}
