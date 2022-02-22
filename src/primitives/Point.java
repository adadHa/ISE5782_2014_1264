package primitives;

import java.util.Objects;

public class Point {
    Double3 x;
    Double3 y;
    Double3 z;

    public Point(Double3 x, Double3 y, Double3 z) {
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


    @Override
    public String toString() {
        return "the point:" +
                "(" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z + "0";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if (!(o instanceof Point)) return false;
        Point other = (Point) o;
        return this.x.equals(other.x) && this.y.equals(other.y) && this.z.equals(other.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
