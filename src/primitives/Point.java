package primitives;

import java.util.*;


/**
 * this class represent a Point in the space
 */
public class Point {

    /**
     * constant for the zero point
     */
    public static final Point ZERO = new Point(Double3.ZERO);

    /**
     * the coordinates of the Point
     */
    final Double3 xyz;

    /**
     * constructor for Point, gets three doubles
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x,y,z);
    }

    /**
     * constructor for Point, gets a Double3
     * @param p
     */
    public Point(Double3 p){
        this.xyz = p;
    }

    /**
     * getter for the coordinates of the Point (double3 type)
     * @return
     */
    public primitives.Double3 getXyz() {
        return xyz;
    }

    /**
     * this function get the vector between our point and the given another point
     * @param another
     * @return subtract result vector
     */
    public Vector subtract(Point another) {
        return new Vector(this.xyz.d1 - another.xyz.d1, this.xyz.d2 - another.xyz.d2, this.xyz.d3 - another.xyz.d3);
    }

    /**
     * this function adds the given Vector v to our Point
     * @param v
     * @return add result Point
     */
    public Point add(Vector v) {
        return new Point(this.xyz.d1 + v.xyz.d1, this.xyz.d2 + v.xyz.d2, this.xyz.d3 + v.xyz.d3);
    }

    /**
     * this function return the scaled distance between our point and between the given another point
     * @param another
     * @return the scaled distance between them
     */
    public double distanceSquared(Point another) {
        double x = this.xyz.d1 - another.xyz.d1;
        double y = this.xyz.d2 - another.xyz.d2;
        double z = this.xyz.d3 - another.xyz.d3;
        return (x * x) + (y * y) + (z * z);
    }

    /**
     * this function return the distance between our point and between the given another point
     * @param another
     * @return the distance between them
     */
    public double distance(Point another) {
        return java.lang.Math.sqrt(distanceSquared(another));
    }

    /**
     * toString function for Point
     * @return
     */
    @Override
    public String toString() {
        return "the point:" +
                "(" +
                "x=" + this.xyz.d1 +
                ", y=" + this.xyz.d2 +
                ", z=" + this.xyz.d3 + ")";
    }

    /**
     * this is equals function for Point
     * @param o
     * @return if this equals o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if (!(o instanceof Point other)) return false;
        return this.xyz.equals(other.xyz) && this.xyz.equals(other.xyz) && this.xyz.equals(other.xyz);
    }

    /**
     * getter for X (the first coordinate from xyz)
     * @return X
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * getter for Y (the second coordinate from xyz)
     * @return Y
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * getter for Z (the third coordinate from xyz)
     * @return Z
     */
    public double getZ() {
        return xyz.d3;
    }
}
