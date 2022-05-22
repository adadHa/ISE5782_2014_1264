package primitives;

import static primitives.Util.isZero;

public class Vector extends Point{

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException();
        }
    }
    public Vector(Double3 v) {
        super(v);
        if (xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("vector 0 exception");
        }
    }
    public Vector add(Vector another) {
        return new Vector(this.xyz.add(another.xyz));
    }

    public Vector subtract(Vector another) {
        return new Vector(this.xyz.subtract(another.xyz));
    }

    public Vector scale(double k){
        return new Vector(this.xyz.scale(k));
    }

    public double dotProduct(Vector v){
        Double3 p = xyz.product(v.xyz);
        return  p.d1 + p.d2 + p.d3;
    }

    public Vector crossProduct(Vector b){
        return new Vector(xyz.d2*b.xyz.d3 - xyz.d3*b.xyz.d2,    //X = this.y*b.z - this.z*b.y
                xyz.d3*b.xyz.d1 - xyz.d1*b.xyz.d3,    //Y = this.z*b.x - this.x*b.z
                xyz.d1*b.xyz.d2 - xyz.d2*b.xyz.d1);   //Z = this.x*b.y - this.y*b.x
    }

    public double lengthSquared(){
        return xyz.d1*xyz.d1 + xyz.d2*xyz.d2 + xyz.d3*xyz.d3;
    }

    public  double length(){
        return Math.sqrt(this.lengthSquared());
    }

    public  Vector normalize(){
        return new Vector(xyz.reduce(length()));
    }

    /**
     * This function rotates a vector around unit vector
     * @param axis
     * @param angle
     * @return
     * code from https://stackoverflow.com/questions/31225062/rotating-a-vector-by-angle-and-axis-in-java
     */
    public Vector rotate(Vector axis, double angle){
        double x, y, z;
        double u, v, w;
        x=getX();y=getY();z=getZ();
        u=axis.getX();v=axis.getY();w=axis.getZ();
        double xPrime = u*(u*x + v*y + w*z)*(1d - Math.cos(angle))
                + x*Math.cos(angle)
                + (-w*y + v*z)*Math.sin(angle);
        double yPrime = v*(u*x + v*y + w*z)*(1d - Math.cos(angle))
                + y*Math.cos(angle)
                + (w*x - u*z)*Math.sin(angle);
        double zPrime = w*(u*x + v*y + w*z)*(1d - Math.cos(angle))
                + z*Math.cos(angle)
                + (-v*x + u*y)*Math.sin(angle);
        return new Vector(xPrime, yPrime, zPrime);
    }

    public Vector findPrependicular(){
        double a, b, c;
        a = getX(); b = getY(); c = getZ();
        if(!isZero(c)){
            return new Vector(1,1,-(a+b)/c).normalize();
        }
        else if(!isZero(b))
        {
            return new Vector(1,-(a+c)/b,1).normalize();
        }
        else{
            return new Vector(-(b+c)/a,1,1).normalize();
        }
    }

    @Override
    public String toString() {
        return "the vector:" +
                "(" +
                "x=" + this.xyz.d1 +
                ", y=" + this.xyz.d2 +
                ", z=" + this.xyz.d3 + ")";
    }

}

