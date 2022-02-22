package primitives;

import java.util.Objects;

public class Ray {
    Point p0;
    Vector dir;

    public Ray(Point p, Vector v) {
        this.p0 = p;
        if(v.length() == 1)
            this.dir = v;
        else
            this.dir = v.normalize();
    }

    public Vector getDir() {
        return dir;
    }
    public Point getP0() {
        return p0;
    }

    @Override
    public String toString() {
        return "Ray {" +
                "point =" + p0.toString() +
                ", dir =" + dir.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null) return false;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

}
