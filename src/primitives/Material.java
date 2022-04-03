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

    public double getkD() {
        return kD;
    }

    public Material setkD(double kD) {
        this.kD = kD;
        return this;
    }

    public double getkS() {
        return kS;
    }

    public Material setkS(double kS) {
        this.kS = kS;
        return this;
    }

    public int getnShininess() {
        return nShininess;
    }

    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
