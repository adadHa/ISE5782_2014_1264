package primitives;

import java.util.Objects;

public class Point {
    final Double3 x;
    final Double3 y;
    final Double3 z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public  subtract(Point p){
        Vector toReturn = new Vector(p.x - this.x, p.y - this.y, p.z - this.z);
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
