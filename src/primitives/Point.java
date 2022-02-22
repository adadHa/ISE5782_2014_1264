package primitives;

import java.util.Objects;

public class Point {

    final Double3 xyz;

    public Point(double x, double y, double z) {
        this.xyz = new Double3(x,y,z);
    }

    //public Double3 getX() {
    //    return x;
    //}
    //public Double3 getY() {
    //    return y;
    //}
    //public Double3 getZ() {
    //    return z;
    //}

    public Vector subtract(Point another) {
        return new Vector(another.xyz.d1 - this.xyz.d1, another.xyz.d2 - this.xyz.d2, another.xyz.d3 - this.xyz.d3);
    }

    public Point add(Vector v) {
        return new Point(this.xyz.d1 + v.xyz.d1, this.xyz.d2 + v.xyz.d2, this.xyz.d3 + v.xyz.d3);
    }

    public double distanceSquared(Point another) {
        double x = this.xyz.d1 - another.xyz.d1;
        double y = this.xyz.d2 - another.xyz.d2;
        double z = this.xyz.d3 - another.xyz.d3;
        return (x * x) + (y * y) + (z * z);
    }

    public double distance(Point another) {
        return java.lang.Math.sqrt(distanceSquared(another));
    }

    @Override
    public String toString() {
        return "the point:" +
                "(" +
                "x=" + this.xyz.d1 +
                ", y=" + this.xyz.d2 +
                ", z=" + this.xyz.d3 + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if (!(o instanceof Point)) return false;
        Point other = (Point) o;
        return this.xyz.equals(other.xyz) && this.xyz.equals(other.xyz) && this.xyz.equals(other.xyz);
    }

}
