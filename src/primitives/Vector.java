package primitives;

import static primitives.Util.isZero;

/**
 * this class represent a Vector in the space, extends point
 */
public class Vector extends Point{

    /**
     * constructor of the Vector, gets the three doubles coordinates, do not accept
     * @param x
     * @param y
     * @param z
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException();
        }
    }

    /**
     * constructor of the Vector, gets Double3
     * @param v
     */
    public Vector(Double3 v) {
        super(v);
        if (xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("vector 0 exception");
        }
    }

    /**
     * add the given another vector to our Vector
     * @param another
     * @return the adding result
     */
    public Vector add(Vector another) {
        return new Vector(this.xyz.add(another.xyz));
    }

    /**
     * subtract the given vector from our Vector
     * @param another
     * @return the subtracting result
     */
    public Vector subtract(Vector another) {
        return new Vector(this.xyz.subtract(another.xyz));
    }

    /**
     * scale our Vector by k
     * @param k
     * @return the scale result
     */
    public Vector scale(double k){
        return new Vector(this.xyz.scale(k));
    }

    /**
     *
     * @param v
     * @return dotProduct between our Vector and given vector v
     */
    public double dotProduct(Vector v){
        Double3 p = xyz.product(v.xyz);
        return  p.d1 + p.d2 + p.d3;
    }

    /**
     *
     * @param b
     * @return crossProduct between our Vector and given vector v
     */
    public Vector crossProduct(Vector b){
        return new Vector(xyz.d2*b.xyz.d3 - xyz.d3*b.xyz.d2,    //X = this.y*b.z - this.z*b.y
                xyz.d3*b.xyz.d1 - xyz.d1*b.xyz.d3,    //Y = this.z*b.x - this.x*b.z
                xyz.d1*b.xyz.d2 - xyz.d2*b.xyz.d1);   //Z = this.x*b.y - this.y*b.x
    }

    /**
     * this function return the squared length of our Vector
     * @return
     */
    public double lengthSquared(){
        return xyz.d1*xyz.d1 + xyz.d2*xyz.d2 + xyz.d3*xyz.d3;
    }

    /**
     * this function return the length of our Vector
     * @return
     */
    public  double length(){
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * the function normalize our Vector
     * @return normalized vector
     */
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

    /**
     * this function search normalized perpendicular vector to our Vector
     * @return the perpendicular Vector
     */
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

    /**
     * toString function for Vector
     * @return
     */
    @Override
    public String toString() {
        return "the vector:" +
                "(" +
                "x=" + this.xyz.d1 +
                ", y=" + this.xyz.d2 +
                ", z=" + this.xyz.d3 + ")";
    }

}

