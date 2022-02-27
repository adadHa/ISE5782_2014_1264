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

    public double dotProduct(Vector a){
        //return new Vector(this.xyz.product())
    }

    public Vector crossProduct(Vector b){
        return new Vector(this.y.product(b.z).subtract(this.z.product(b.y)),    //X = this.y*b.z - this.z*b.y
                this.z.product(b.x).subtract(this.x.product(b.z)),    //Y = this.z*b.x - this.x*b.z
                this.x.product(b.y).subtract(this.y.product(b.x)));   //Z = this.z*b.y - this.y*b.x
    }

    public double lengthSquared(){
        return dotProduct(this);
    }

    public  double length(){
        return Math.sqrt(this.lengthSquared());
    }

    public  Vector normalize(){
        return new Vector(xyz.reduce(length()));
    }


}
