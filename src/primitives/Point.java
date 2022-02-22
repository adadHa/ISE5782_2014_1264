package primitives;

import java.util.Objects;

public class Point {

    final Double3 point;

    public Point(double x, double y, double z) {
        this.point = new Double3(x,y,z);
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

    public Vector subtract(Point another){
        return new Vector(another.point.d1 - this.point.d1, another.point.d2 - this.point.d2, another.point.d3 - this.point.d3);
    }

    public Point Add(Vector v){
        return new Point(this.point.d1 + v.point.d1, this.point.d2 + v.point.d2, this.point.d3 + v.point.d3);
    }

    public double DistanceSquared(Point p){

    }

    @Override
    public String toString() {
        return "the point:" +
                "(" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if (!(o instanceof Point)) return false;
        Point other = (Point) o;
        return this.x.equals(other.x) && this.y.equals(other.y) && this.z.equals(other.z);
    }

}
