package primitives;

public class Vector extends Point{

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Vector 0!");
        }
    }
    public Vector(Double3 v) {
        super(v);
        if (xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("Vector 0!");
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
                xyz.d3*b.xyz.d2 - xyz.d2*b.xyz.d1);   //Z = this.z*b.y - this.y*b.x
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


}
