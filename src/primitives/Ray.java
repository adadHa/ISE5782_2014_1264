package primitives;

import java.util.Objects;

public class Ray {
    final Point p0;
    final Vector dir;

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
        return " the Ray: " +
                "point is:" + p0.toString() +
                ", direction vector is: " + dir.toString() +
                '.';
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
