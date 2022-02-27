package primitives;

public class Vector extends Point{

    public Vector(double x, double y, double z) {
        super(x, y, z);
    }
    public Vector(Double3 v) {
        if (v.equals((obj)Double3.ZERO)){
            //throw Ill
        }
        super(v.d1, v.d2, v.d3);
    }
    public Vector add(Vector another) {
        return new Vector(this.x.add(another.x),this.y.add(another.y),this.z.add(another.z));
    }

    public Vector subtract(Vector another) {
        return new Vector(this.x.subtract(another.x),this.y.subtract(another.y),this.z.subtract(another.z));
    }

    public Vector scale(double k){
        return new Vector(this.x.scale(k),this.y.scale(k),this.z.scale(k));
    }

    public Vector crossProduct(Vector a){
        return new Vector(this.x.scale(k),this.y.scale(k),this.z.scale(k));
    }

    public double lengthSquared(){

    }

    public  double length(){

    }

    public  Vector normalize(){

    }

    public double dotProduct(Vector a){

    }
}
