package primitives;

/**
 * This method represents a material of geometries. The material
 * characteristic the shininess and k's of reflectance and refraction
 */
public class Material {
    /**
     *
     */
    public double kD = 0;
    /**
     *
     */
    public double kS = 0;
    /**
     * shininess scale factor of the material
     */
    public int nShininess = 0;

    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }

    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Transparency factor
     */
    public Double3 kT = Double3.ZERO;

    /**
     * Reflection factor
     */
    public Double3 kR = Double3.ONE;


    public double getkD() {
        return kD;
    }

    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    public double getkS() {
        return kS;
    }

    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    public int getnShininess() {
        return nShininess;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
