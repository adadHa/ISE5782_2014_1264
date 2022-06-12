package primitives;

/**
 * This method represents a material of geometries. The material
 * characteristic the shininess and k's of reflectance and refraction
 */
public class Material {
    /**
     * diffuse effect of the light on the Material, scale factor
     */
    public double kD = 0;
    /**
     * specular effect of the light on the Material, scale factor
     */
    public double kS = 0;
    /**
     * shininess scale factor of the material
     */
    public int nShininess = 0;
    /**
     * glossiness constant of the material
     */
    public double glossiness = 0;

    /**
     * blurrier constant of the material
     */
    public double blurriness = 0;

    /**
     * Transparency factor
     */
    public Double3 kT = Double3.ZERO;

    /**
     * Reflection factor
     */
    public Double3 kR = Double3.ZERO;

    /**
     * setter method for the Glossiness of the Material
     * @param glossiness
     * @return the Material after
     */
    public Material setGlossiness(double glossiness) {
        this.glossiness = glossiness;
        return this;
    }

    /**
     * setter method for the Blurriness of the Material
     * @param blurriness
     * @return the Material after
     */
    public Material setBlurriness(double blurriness) {
        this.blurriness = blurriness;
        return this;
    }


    /**
     * setter method for kT, get Double3 (the Transparency of the Material)
     * @param kT, Double3
     * @return the Material after
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * setter method for kT, get double (the Transparency of the Material)
     * @param kT, double        (convert to Double3)
     * @return the Material after
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * setter method for kR, get Double3 (the reflection factor)
     * @param kR, Double3
     * @return the Material after
     */
        public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * setter method for kR, get double (the reflection factor)
     * @param kR, double        (convert to Double3)
     * @return the Material after
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }


    /**
     * getter for the kD (diffuse effect on the Material)
     * @return
     */
    public double getkD() {
        return kD;
    }

    /**
     * setter method for the kD (the diffuse effect on the Material)
     * @param kD
     * @return the Material after
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * getter for the kS (specular effect on the Material)
     * @return
     */
    public double getkS() {
        return kS;
    }

    /**
     * setter method for the kS (specular effect on the Material)
     * @param kS
     * @return the Material after
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * getter method for the Shininess
     * @return
     */
    public int getnShininess() {
        return nShininess;
    }

    /**
     * setter method for the Shininess
     * @param nShininess
     * @return the Material after
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
